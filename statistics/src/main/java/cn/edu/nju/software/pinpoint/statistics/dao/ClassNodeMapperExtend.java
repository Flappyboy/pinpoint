package cn.edu.nju.software.pinpoint.statistics.dao;

import cn.edu.nju.software.pinpoint.statistics.entity.ClassNode;
import cn.edu.nju.software.pinpoint.statistics.entity.ClassNodeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassNodeMapperExtend {
    int insertBatch(List<ClassNode> classNodeList);
}