package cn.edu.nju.software.pinpoint.statistics.dao;

import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResultEdgeCall;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResultEdgeCallExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PartitionResultEdgeCallMapper {
    int countByExample(PartitionResultEdgeCallExample example);

    int deleteByExample(PartitionResultEdgeCallExample example);

    int deleteByPrimaryKey(String id);

    int insert(PartitionResultEdgeCall record);

    int insertSelective(PartitionResultEdgeCall record);

    List<PartitionResultEdgeCall> selectByExample(PartitionResultEdgeCallExample example);

    PartitionResultEdgeCall selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PartitionResultEdgeCall record, @Param("example") PartitionResultEdgeCallExample example);

    int updateByExample(@Param("record") PartitionResultEdgeCall record, @Param("example") PartitionResultEdgeCallExample example);

    int updateByPrimaryKeySelective(PartitionResultEdgeCall record);

    int updateByPrimaryKey(PartitionResultEdgeCall record);
}