package cn.edu.nju.software.pinpoint.statistics.dao;

import cn.edu.nju.software.pinpoint.statistics.entity.AlgorithmsParam;
import cn.edu.nju.software.pinpoint.statistics.entity.AlgorithmsParamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AlgorithmsParamMapper {
    int countByExample(AlgorithmsParamExample example);

    int deleteByExample(AlgorithmsParamExample example);

    int insert(AlgorithmsParam record);

    int insertSelective(AlgorithmsParam record);

    List<AlgorithmsParam> selectByExample(AlgorithmsParamExample example);

    int updateByExampleSelective(@Param("record") AlgorithmsParam record, @Param("example") AlgorithmsParamExample example);

    int updateByExample(@Param("record") AlgorithmsParam record, @Param("example") AlgorithmsParamExample example);
}