package cn.edu.nju.software.pinpoint.statistics.dao;

import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResultEdge;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResultEdgeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PartitionResultEdgeMapper {
    int countByExample(PartitionResultEdgeExample example);

    int deleteByExample(PartitionResultEdgeExample example);

    int deleteByPrimaryKey(String id);

    int insert(PartitionResultEdge record);

    int insertSelective(PartitionResultEdge record);

    List<PartitionResultEdge> selectByExample(PartitionResultEdgeExample example);

    PartitionResultEdge selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PartitionResultEdge record, @Param("example") PartitionResultEdgeExample example);

    int updateByExample(@Param("record") PartitionResultEdge record, @Param("example") PartitionResultEdgeExample example);

    int updateByPrimaryKeySelective(PartitionResultEdge record);

    int updateByPrimaryKey(PartitionResultEdge record);
}