package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.dao.ClassNodeMapper;
import cn.edu.nju.software.pinpoint.statistics.dao.DynamicAnalysisInfoMapper;
import cn.edu.nju.software.pinpoint.statistics.dao.DynamicCallInfoMapper;
import cn.edu.nju.software.pinpoint.statistics.dao.MethodNodeMapper;
import cn.edu.nju.software.pinpoint.statistics.dto.DynamicCallInfoResult;
import cn.edu.nju.software.pinpoint.statistics.entity.*;
import cn.edu.nju.software.pinpoint.statistics.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Slf4j
@Service
public class DynamicCallServiceImpl implements DynamicCallService {

    @Value("${pinpoint.url}")
    private String PINPOINT_URL;

    @Autowired
    private AppService appService;

    @Autowired
    private DynamicCallInfoMapper dynamicCallInfoMapper;
    @Autowired
    private DynamicAnalysisInfoMapper dynamicAnalysisInfoMapper;
    @Autowired
    private Sid sid;
    @Autowired
    private ClassNodeService classNodeService;
    @Autowired
    private MethodNodeService methodNodeService;
    @Autowired
    private ClassNodeMapper classNodeMapper;
    @Autowired
    private MethodNodeMapper methodNodeMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDCallInfo(DynamicCallInfo dynamicCallInfo) {
        String id = sid.nextShort();
//        int flag = dynamicCallInfo.getFlag();
        dynamicCallInfo.setId(id);
        dynamicCallInfo.setCreatedat(new Date());
        dynamicCallInfo.setUpdatedat(new Date());
        dynamicCallInfo.setFlag(1);
        int type = dynamicCallInfo.getType();
        String callerId = "";
        String calleeId = "";
        DynamicAnalysisInfo dinfo = dynamicAnalysisInfoMapper.selectByPrimaryKey(dynamicCallInfo.getDynamicanalysisinfoid());
        if (type == 0) {
            List<ClassNode> c1 = classNodeService.findBycondition(dynamicCallInfo.getCaller(), dinfo.getAppid());
            List<ClassNode> c2 = classNodeService.findBycondition(dynamicCallInfo.getCallee(), dinfo.getAppid());
            if (c1.size() > 0 && c1 != null)
                callerId = c1.get(0).getId();
            if (c2.size() > 0 && c2 != null)
                calleeId = c2.get(0).getId();
        } else if (type == 1) {
            List<MethodNode> m1 = methodNodeService.findByCondition(dynamicCallInfo.getCaller(), null, dinfo.getAppid());
            List<MethodNode> m2 = methodNodeService.findByCondition(dynamicCallInfo.getCallee(), null, dinfo.getAppid());
            if (m1.size() > 0 && m1 != null)
                callerId = m1.get(0).getId();
            if (m1.size() > 0 && m1 != null)
                calleeId = m2.get(0).getId();
        }
        System.out.println(callerId);
        System.out.println(calleeId);
        DynamicCallInfoExample dynamicCallInfoExample = new DynamicCallInfoExample();
        DynamicCallInfoExample.Criteria dynamicCallInfoCriteria = dynamicCallInfoExample.createCriteria();
        dynamicCallInfoCriteria.andCallerEqualTo(callerId).andCalleeEqualTo(calleeId).andFlagEqualTo(1)
        .andTypeEqualTo(type).andDynamicanalysisinfoidEqualTo(dynamicCallInfo.getDynamicanalysisinfoid());
        List<DynamicCallInfo> ds = dynamicCallInfoMapper.selectByExample(dynamicCallInfoExample);
        if(ds==null||ds.size()<=0) {
            dynamicCallInfo.setCaller(callerId);
            dynamicCallInfo.setCallee(calleeId);
            dynamicCallInfoMapper.insert(dynamicCallInfo);
        }else{
            DynamicCallInfo dci = ds.get(0);
            int count = dci.getCount() + dynamicCallInfo.getCount();
            dci.setCount(count);
            dci.setUpdatedat(new Date());
            dynamicCallInfoMapper.updateByPrimaryKeySelective(dci);
        }

//        if (flag == 0) {
//            DynamicAnalysisInfo ainfo = new DynamicAnalysisInfo();
//            ainfo.setId(dinfo.getId());
//            ainfo.setStatus(1);
//            ainfo.setUpdatedat(new Date());
//            System.out.println(ainfo);
//            dynamicAnalysisInfoMapper.updateByPrimaryKeySelective(ainfo);
//        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void statisticsCallInfo(DynamicAnalysisInfo dynamicAnalysisInfo) {
        ObjectMapper MAPPER = new ObjectMapper();
        App app = appService.queryAppById(dynamicAnalysisInfo.getAppid());
        if(app==null){
            log.error("no app for appid="+dynamicAnalysisInfo.getAppid());
        }
        List<ClassNode> classNodeList = classNodeService.findByAppid(app.getId());
        List<String> classNameList = new ArrayList<>();
        for (ClassNode node: classNodeList) {
            classNameList.add(node.getName());
        }

        Map<String, Object> map = new HashMap<>();

        map.put("an",app.getName());
        try {
            map.put("pnList",MAPPER.writeValueAsString(classNameList));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        map.put("analysisId",dynamicAnalysisInfo.getId());

        String json = doPost(PINPOINT_URL+"/statistcsallcall.pinpoint",map);
        log.debug(json);

        try {
            DynamicCallInfoResult result = MAPPER.readValue(json, DynamicCallInfoResult.class);
            List<DynamicCallInfo> dynamicCallInfoList = result.getData().getList();
            for (DynamicCallInfo dynamicCall :
                    dynamicCallInfoList) {
                saveDCallInfo(dynamicCall);
            }
            if (result.getData().getFlag() == 0) {
                DynamicAnalysisInfo ainfo = new DynamicAnalysisInfo();
                ainfo.setId(dynamicAnalysisInfo.getId());
                ainfo.setStatus(1);
                ainfo.setUpdatedat(new Date());
                System.out.println(ainfo);
                dynamicAnalysisInfoMapper.updateByPrimaryKeySelective(ainfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDCallInfo(DynamicCallInfo dynamicCallInfo) {
        dynamicCallInfo.setUpdatedat(new Date());
        DynamicCallInfoExample example = new DynamicCallInfoExample();
        DynamicCallInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(dynamicCallInfo.getId()).andFlagEqualTo(1);
        dynamicCallInfoMapper.updateByExampleSelective(dynamicCallInfo, example);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteDCallInfo(String dynamicCallInfoId) {
        DynamicCallInfo dynamicCallInfo = new DynamicCallInfo();
        dynamicCallInfo.setId(dynamicCallInfoId);
        dynamicCallInfo.setFlag(0);
        dynamicCallInfo.setUpdatedat(new Date());
        DynamicCallInfoExample example = new DynamicCallInfoExample();
        DynamicCallInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(dynamicCallInfoId).andFlagEqualTo(1);
        dynamicCallInfoMapper.updateByExampleSelective(dynamicCallInfo, example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public DynamicCallInfo queryDCallInfo(String dynamicCallInfoId) {
        DynamicCallInfo dAnalysisInfo = new DynamicCallInfo();
        DynamicCallInfoExample example = new DynamicCallInfoExample();
        DynamicCallInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(dynamicCallInfoId).andFlagEqualTo(1);
        List<DynamicCallInfo> apps = dynamicCallInfoMapper.selectByExample(example);
        if (apps.size() > 0 && apps != null)
            dAnalysisInfo = apps.get(0);
        return dAnalysisInfo;
    }

    @Override
    public DynamicCallInfo queryCallById(String id) {
        DynamicCallInfo dynamicCallInfo = dynamicCallInfoMapper.selectByPrimaryKey(id);
        if (dynamicCallInfo!=null){
            dynamicCallInfo.setCallerObj(queryCallObj(dynamicCallInfo.getCaller(),dynamicCallInfo.getType()));
            dynamicCallInfo.setCalleeObj(queryCallObj(dynamicCallInfo.getCallee(),dynamicCallInfo.getType()));
        }
        return dynamicCallInfo;
    }
    private Object queryCallObj(String id, int type){
        if(type==0){
            return classNodeMapper.selectByPrimaryKey(id);
        }else{
            return methodNodeMapper.selectByPrimaryKey(id);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<DynamicCallInfo> queryDCallInfo(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        DynamicCallInfoExample example = new DynamicCallInfoExample();
        DynamicCallInfoExample.Criteria criteria = example.createCriteria();
        criteria.andFlagEqualTo(1);
        example.setOrderByClause("createdat");
        List<DynamicCallInfo> dynamicCallInfoList = dynamicCallInfoMapper.selectByExample(example);
        return dynamicCallInfoList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<HashMap<String, String>> findEdgeByAppId(String dynamicAnalysisInfoId, int page, int pageSize, int type) {
        List<HashMap<String, String>> edges = new ArrayList<>();
        PageHelper.startPage(page, pageSize);
        DynamicCallInfoExample example = new DynamicCallInfoExample();
        DynamicCallInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDynamicanalysisinfoidEqualTo(dynamicAnalysisInfoId)
                .andFlagEqualTo(1).andTypeEqualTo(type);
        example.setOrderByClause("count desc");
        List<DynamicCallInfo> scinfos = dynamicCallInfoMapper.selectByExample(example);

        for (DynamicCallInfo scinfo : scinfos) {
            HashMap<String, String> edge = new HashMap<String, String>();
            if (type == 0) {
                ClassNode callerNode = classNodeService.findById(scinfo.getCaller());
                ClassNode calleeNode = classNodeService.findById(scinfo.getCallee());
                edge.put("caller", callerNode.getName());
                edge.put("callee", calleeNode.getName());
                edge.put("count", String.valueOf(scinfo.getCount()));
            }
            if (type == 1) {
                MethodNode callerNode = methodNodeService.findById(scinfo.getCaller());
                MethodNode calleeNode = methodNodeService.findById(scinfo.getCallee());
                edge.put("caller", callerNode.getName());
                edge.put("callee", calleeNode.getName());
                edge.put("count", String.valueOf(scinfo.getCount()));
            }
            edges.add(edge);
        }
        return edges;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int countOfDynamicCall(String dynamicAnalysisInfoId, int type) {
        DynamicCallInfoExample example = new DynamicCallInfoExample();
        DynamicCallInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDynamicanalysisinfoidEqualTo(dynamicAnalysisInfoId)
                .andFlagEqualTo(1).andTypeEqualTo(type);
        return dynamicCallInfoMapper.countByExample(example);
    }

    public static String doPost(String url, Map<String, Object> paramMap) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        // 封装post请求参数
        if (null != paramMap && paramMap.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            // 通过map集成entrySet方法获取entity
            Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
            // 循环遍历，获取迭代器
            Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> mapEntry = iterator.next();
                nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
            }

            // 为httpPost设置封装好的请求参数
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String doGet(String url) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            // 设置请求头信息，鉴权
//            httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        DynamicCallServiceImpl dynamicCallService = new DynamicCallServiceImpl();
        DynamicAnalysisInfo dynamicAnalysisInfo = new DynamicAnalysisInfo();
        dynamicAnalysisInfo.setId("1");
        dynamicAnalysisInfo.setAppid("190213BH898H4DAW");
        dynamicCallService.statisticsCallInfo(dynamicAnalysisInfo);
    }
}
