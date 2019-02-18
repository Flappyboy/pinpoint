package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.dao.PartitionResultEdgeCallMapper;
import cn.edu.nju.software.pinpoint.statistics.dao.PartitionResultEdgeMapper;
import cn.edu.nju.software.pinpoint.statistics.dao.PartitionResultEdgeMapperExtend;
import cn.edu.nju.software.pinpoint.statistics.entity.*;
import cn.edu.nju.software.pinpoint.statistics.service.DynamicCallService;
import cn.edu.nju.software.pinpoint.statistics.service.PartitionResultEdgeService;
import cn.edu.nju.software.pinpoint.statistics.service.PartitionService;
import cn.edu.nju.software.pinpoint.statistics.service.StaticCallService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PartitionResultEdgeServiceImpl implements PartitionResultEdgeService {

    @Autowired
    private PartitionResultEdgeMapper partitionResultEdgeMapper;

    @Autowired
    private PartitionResultEdgeMapperExtend partitionResultEdgeMapperExtend;

    @Autowired
    private PartitionResultEdgeCallMapper partitionResultEdgeCallMapper;

    @Autowired
    private PartitionService partitionService;

    @Autowired
    private StaticCallService staticCallService;

    @Autowired
    private DynamicCallService dynamicCallService;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void statisticsPartitionResultEdge(PartitionInfo partitionInfo) {
//        PartitionInfo partitionInfo = partitionService.findPartitionById(partitionId);
        List<PartitionResultEdge> partitionResultEdgeFromStaticList = partitionResultEdgeMapperExtend.statisticsEdgesFromStatic(partitionInfo.getId(), partitionInfo.getAppid());
        for (PartitionResultEdge p: partitionResultEdgeFromStaticList) {
            if(p.getPatitionresultaid().equals(p.getPatitionresultbid()))
                continue;
            p.setId(sid.nextShort());
            p.setCreatedat(new Date());
            p.setUpdatedat(new Date());
            partitionResultEdgeMapper.insert(p);
            List<StaticCallInfo> staticCallInfoList = p.getStaticCallInfoList();
            for(StaticCallInfo staticCallInfo: staticCallInfoList){
                PartitionResultEdgeCall partitionResultEdgeCall = new PartitionResultEdgeCall();
                partitionResultEdgeCall.setId(sid.nextShort());
                partitionResultEdgeCall.setCallid(staticCallInfo.getId());
                partitionResultEdgeCall.setEdgeid(p.getId());
                partitionResultEdgeCall.setCalltype(staticCallInfo.getType());
                partitionResultEdgeCall.setCreatedat(new Date());
                partitionResultEdgeCall.setUpdatedat(new Date());
                partitionResultEdgeCallMapper.insert(partitionResultEdgeCall);
            }
        }

        List<PartitionResultEdge> partitionResultEdgeFromDynamicList = partitionResultEdgeMapperExtend.statisticsEdgesFromDynamic(partitionInfo.getId(), partitionInfo.getDynamicanalysisinfoid());
        for (PartitionResultEdge dynamicEdge: partitionResultEdgeFromDynamicList) {
            if(dynamicEdge.getPatitionresultaid().equals(dynamicEdge.getPatitionresultbid()))
                continue;
            boolean continueFlag = false;
            for (int i=0; i<partitionResultEdgeFromStaticList.size(); i++){
                PartitionResultEdge staticEdge = partitionResultEdgeFromStaticList.get(i);
                if(staticEdge.getPatitionresultaid().equals(dynamicEdge.getPatitionresultbid()) && staticEdge.getPatitionresultbid().equals(dynamicEdge.getPatitionresultbid())){
                    dynamicEdge.setId(staticEdge.getId());
                    List<DynamicCallInfo> dynamicCallInfoList = dynamicEdge.getDynamicCallInfoList();
                    for(DynamicCallInfo dynamicCallInfo: dynamicCallInfoList){
                        List<StaticCallInfo> staticCallInfoList = staticEdge.getStaticCallInfoList();
                        boolean continueFlag2 = false;
                        for(StaticCallInfo staticCallInfo: staticCallInfoList){
                            if(staticCallInfo.getCaller().equals(dynamicCallInfo.getCaller()) && staticCallInfo.getCallee().equals(dynamicCallInfo.getCallee())) {
                                continueFlag2 = true;
                                break;
                            }
                        }
                        if (continueFlag2){
                            continue;
                        }
                        PartitionResultEdgeCall partitionResultEdgeCall = new PartitionResultEdgeCall();
                        partitionResultEdgeCall.setId(sid.nextShort());
                        partitionResultEdgeCall.setCallid(dynamicCallInfo.getId());
                        partitionResultEdgeCall.setEdgeid(dynamicEdge.getId());
                        partitionResultEdgeCall.setCalltype(dynamicCallInfo.getType());
                        partitionResultEdgeCall.setCreatedat(new Date());
                        partitionResultEdgeCall.setUpdatedat(new Date());
                        partitionResultEdgeCallMapper.insert(partitionResultEdgeCall);
                    }
                    continueFlag=true;
                    break;
                }else{
                    continue;
                }
            }
            if(continueFlag)
                continue;

            dynamicEdge.setId(sid.nextShort());
            dynamicEdge.setCreatedat(new Date());
            dynamicEdge.setUpdatedat(new Date());
            partitionResultEdgeMapper.insert(dynamicEdge);
            List<DynamicCallInfo> dynamicCallInfoList = dynamicEdge.getDynamicCallInfoList();
            for(DynamicCallInfo dynamicCallInfo: dynamicCallInfoList){
                PartitionResultEdgeCall partitionResultEdgeCall = new PartitionResultEdgeCall();
                partitionResultEdgeCall.setId(sid.nextShort());
                partitionResultEdgeCall.setCallid(dynamicCallInfo.getId());
                partitionResultEdgeCall.setEdgeid(dynamicEdge.getId());
                partitionResultEdgeCall.setCalltype(dynamicCallInfo.getType());
                partitionResultEdgeCall.setCreatedat(new Date());
                partitionResultEdgeCall.setUpdatedat(new Date());
                partitionResultEdgeCallMapper.insert(partitionResultEdgeCall);
            }
        }
    }

    @Override
    public List<PartitionResultEdge> findPartitionResultEdge(String partitionId) {

        return partitionResultEdgeMapperExtend.queryEdgeByPartitionId(partitionId);
    }

    @Override
    public List<PartitionResultEdgeCall> findPartitionResultEdgeCallByEdgeId(String edgeId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        PartitionResultEdgeCallExample example = new PartitionResultEdgeCallExample();
        PartitionResultEdgeCallExample.Criteria criteria= example.createCriteria();
        criteria.andEdgeidEqualTo(edgeId);
        List<PartitionResultEdgeCall> partitionResultEdgeCallList = partitionResultEdgeCallMapper.selectByExample(example);
        for (PartitionResultEdgeCall p :
                partitionResultEdgeCallList) {
            Object call=staticCallService.queryCallById(p.getCallid());
            if(call==null) {
//                DynamicCallInfo dynamicCallInfo = dynamicCallInfoMapper.selectByPrimaryKey(p.getCallid());
                call=dynamicCallService.queryCallById(p.getCallid());
                if(call==null){
                    log.error("call data wrong");
                }
            }
            p.setCall(call);
        }
        return partitionResultEdgeCallList;
    }

    @Override
    public int countOfPartitionResultEdgeCallByEdgeId(String edgeId) {
        PartitionResultEdgeCallExample example = new PartitionResultEdgeCallExample();
        PartitionResultEdgeCallExample.Criteria criteria= example.createCriteria();
        criteria.andEdgeidEqualTo(edgeId);
        return partitionResultEdgeCallMapper.countByExample(example);
    }

}
