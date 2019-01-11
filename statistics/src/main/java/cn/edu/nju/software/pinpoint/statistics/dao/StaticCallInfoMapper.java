package cn.edu.nju.software.pinpoint.statistics.dao;

import cn.edu.nju.software.pinpoint.statistics.entity.StaticCallInfo;
import cn.edu.nju.software.pinpoint.statistics.entity.StaticCallInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StaticCallInfoMapper {
    int countByExample(StaticCallInfoExample example);

    int deleteByExample(StaticCallInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(StaticCallInfo record);

    int insertSelective(StaticCallInfo record);

    List<StaticCallInfo> selectByExample(StaticCallInfoExample example);

    StaticCallInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") StaticCallInfo record, @Param("example") StaticCallInfoExample example);

    int updateByExample(@Param("record") StaticCallInfo record, @Param("example") StaticCallInfoExample example);

    int updateByPrimaryKeySelective(StaticCallInfo record);

    int updateByPrimaryKey(StaticCallInfo record);
}