package cn.edu.nju.software.pinpoint.statistics.dao;

import cn.edu.nju.software.pinpoint.statistics.entity.DynamicCallInfo;
import cn.edu.nju.software.pinpoint.statistics.entity.DynamicCallInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DynamicCallInfoMapper {
    int countByExample(DynamicCallInfoExample example);

    int deleteByExample(DynamicCallInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(DynamicCallInfo record);

    int insertSelective(DynamicCallInfo record);

    List<DynamicCallInfo> selectByExample(DynamicCallInfoExample example);

    DynamicCallInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DynamicCallInfo record, @Param("example") DynamicCallInfoExample example);

    int updateByExample(@Param("record") DynamicCallInfo record, @Param("example") DynamicCallInfoExample example);

    int updateByPrimaryKeySelective(DynamicCallInfo record);

    int updateByPrimaryKey(DynamicCallInfo record);
}