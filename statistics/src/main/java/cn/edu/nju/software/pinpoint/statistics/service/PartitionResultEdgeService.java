package cn.edu.nju.software.pinpoint.statistics.service;


import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResultEdge;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResultEdgeCall;

import java.util.List;

public interface PartitionResultEdgeService {

    void statisticsPartitionResultEdge(String partitionResultId);

    List<PartitionResultEdge> findPartitionResultEdge(String partitionId);

    List<PartitionResultEdgeCall> findPartitionResultEdgeCall(String edgeId);

}
