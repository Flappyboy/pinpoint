package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.dao.PartitionResultEdgeCallMapper;
import cn.edu.nju.software.pinpoint.statistics.dao.PartitionResultEdgeMapper;
import cn.edu.nju.software.pinpoint.statistics.dao.PartitionResultEdgeMapperExtend;
import cn.edu.nju.software.pinpoint.statistics.entity.*;
import cn.edu.nju.software.pinpoint.statistics.service.PartitionResultEdgeService;
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
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void statisticsPartitionResultEdge(String partitionResultId) {
        List<PartitionResultEdge> partitionResultEdgeList = partitionResultEdgeMapperExtend.statisticsEdges();
        for (PartitionResultEdge p: partitionResultEdgeList) {
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
    }

    @Override
    public List<PartitionResultEdge> findPartitionResultEdge(String partitionId) {

        return partitionResultEdgeMapperExtend.queryEdgeByPartitionId(partitionId);
    }

    @Override
    public List<PartitionResultEdgeCall> findPartitionResultEdgeCall(String edgeId) {
        PartitionResultEdgeCallExample example = new PartitionResultEdgeCallExample();
        PartitionResultEdgeCallExample.Criteria criteria= example.createCriteria();
        criteria.andEdgeidEqualTo(edgeId);
        return partitionResultEdgeCallMapper.selectByExample(example);
    }

}
