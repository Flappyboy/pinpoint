package cn.edu.nju.software.pinpoint.statistics.service;


import cn.edu.nju.software.pinpoint.statistics.entity.PartitionInfo;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResultEdge;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResultEdgeCall;

import java.util.List;

public interface PartitionResultEdgeService {

    void statisticsPartitionResultEdge(PartitionInfo partitionInfo);

    List<PartitionResultEdge> findPartitionResultEdge(String partitionId);

    List<PartitionResultEdgeCall> findPartitionResultEdgeCallByEdgeId(String edgeId, Integer page, Integer pageSize);
    int countOfPartitionResultEdgeCallByEdgeId(String edgeId);

}
