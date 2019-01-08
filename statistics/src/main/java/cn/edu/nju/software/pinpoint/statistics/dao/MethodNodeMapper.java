package cn.edu.nju.software.pinpoint.statistics.dao;

import cn.edu.nju.software.pinpoint.statistics.entity.MethodNode;
import cn.edu.nju.software.pinpoint.statistics.entity.MethodNodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MethodNodeMapper {
    int countByExample(MethodNodeExample example);

    int deleteByExample(MethodNodeExample example);

    int insert(MethodNode record);

    int insertSelective(MethodNode record);

    List<MethodNode> selectByExample(MethodNodeExample example);

    int updateByExampleSelective(@Param("record") MethodNode record, @Param("example") MethodNodeExample example);

    int updateByExample(@Param("record") MethodNode record, @Param("example") MethodNodeExample example);
}