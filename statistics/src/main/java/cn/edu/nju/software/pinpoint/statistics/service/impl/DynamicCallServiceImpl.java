package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.dao.DynamicAnalysisInfoMapper;
import cn.edu.nju.software.pinpoint.statistics.dao.DynamicCallInfoMapper;
import cn.edu.nju.software.pinpoint.statistics.entity.*;
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
    private DynamicAnalysisInfoMapper dynamicAnalysisInfoMapper;
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
        int flag = dynamicCallInfo.getFlag();
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
        dynamicCallInfo.setCaller(callerId);
        dynamicCallInfo.setCallee(calleeId);
        dynamicCallInfoMapper.insert(dynamicCallInfo);
        if (flag == 0) {
            DynamicAnalysisInfo ainfo = new DynamicAnalysisInfo();
            ainfo.setId(dinfo.getId());
            ainfo.setStatus(1);
            ainfo.setUpdatedat(new Date());
            System.out.println(ainfo);
            dynamicAnalysisInfoMapper.updateByPrimaryKeySelective(ainfo);
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int countOfDynamicCall(String dynamicAnalysisInfoId, int type) {
        DynamicCallInfoExample example = new DynamicCallInfoExample();
        DynamicCallInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDynamicanalysisinfoidEqualTo(dynamicAnalysisInfoId)
                .andFlagEqualTo(1).andTypeEqualTo(type);
        return dynamicCallInfoMapper.countByExample(example);
    }
}
