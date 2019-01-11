package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.dao.DynamicCallInfoMapper;
import cn.edu.nju.software.pinpoint.statistics.entity.ClassNode;
import cn.edu.nju.software.pinpoint.statistics.entity.DynamicCallInfo;
import cn.edu.nju.software.pinpoint.statistics.entity.DynamicCallInfoExample;
import cn.edu.nju.software.pinpoint.statistics.entity.MethodNode;
import cn.edu.nju.software.pinpoint.statistics.service.ClassNodeService;
import cn.edu.nju.software.pinpoint.statistics.service.DynamicCallService;
import cn.edu.nju.software.pinpoint.statistics.service.MethodNodeService;
import com.github.pagehelper.PageHelper;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
public class DynamicCallServiceImpl implements DynamicCallService {

    @Autowired
    private DynamicCallInfoMapper dynamicCallInfoMapper;
    @Autowired
    private Sid sid;
    @Autowired
    private ClassNodeService classNodeService;
    @Autowired
    private MethodNodeService methodNodeService;
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDCallInfo(DynamicCallInfo dynamicCallInfo) {
        String id = sid.nextShort();
        dynamicCallInfo.setId(id);
        dynamicCallInfo.setCreatedat(new Date());
        dynamicCallInfo.setUpdatedat(new Date());
        dynamicCallInfo.setFlag(1);
        dynamicCallInfoMapper.insert(dynamicCallInfo);
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
        example.setOrderByClause("count");
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
}
