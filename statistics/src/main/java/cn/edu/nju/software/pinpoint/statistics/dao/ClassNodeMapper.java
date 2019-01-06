package cn.edu.nju.software.pinpoint.statistics.dao;

import cn.edu.nju.software.pinpoint.statistics.entity.ClassNode;
import cn.edu.nju.software.pinpoint.statistics.entity.ClassNodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClassNodeMapper {
    int countByExample(ClassNodeExample example);

    int deleteByExample(ClassNodeExample example);

    int insert(ClassNode record);

    int insertSelective(ClassNode record);

    List<ClassNode> selectByExample(ClassNodeExample example);

    int updateByExampleSelective(@Param("record") ClassNode record, @Param("example") ClassNodeExample example);

    int updateByExample(@Param("record") ClassNode record, @Param("example") ClassNodeExample example);
}