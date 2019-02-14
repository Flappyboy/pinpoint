package cn.edu.nju.software.pinpoint.statistics.dao;

import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResultEdge;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResultEdgeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartitionResultEdgeMapperExtend {
    List<PartitionResultEdge> statisticsEdges();

    List<PartitionResultEdge> queryEdgeByPartitionId(String partitionId);
}