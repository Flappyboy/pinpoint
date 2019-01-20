package cn.edu.nju.software.pinpoint.statistics.dao;

import cn.edu.nju.software.pinpoint.statistics.entity.Partition;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PartitionMapper {
    int countByExample(PartitionExample example);

    int deleteByExample(PartitionExample example);

    int deleteByPrimaryKey(String id);

    int insert(Partition record);

    int insertSelective(Partition record);

    List<Partition> selectByExample(PartitionExample example);

    Partition selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Partition record, @Param("example") PartitionExample example);

    int updateByExample(@Param("record") Partition record, @Param("example") PartitionExample example);

    int updateByPrimaryKeySelective(Partition record);

    int updateByPrimaryKey(Partition record);


}