package cn.edu.nju.software.pinpoint.statistics.dao;

import cn.edu.nju.software.pinpoint.statistics.entity.App;
import cn.edu.nju.software.pinpoint.statistics.entity.AppExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppMapper {
    int countByExample(AppExample example);

    int deleteByExample(AppExample example);

    int insert(App record);

    int insertSelective(App record);

    List<App> selectByExample(AppExample example);

    int updateByExampleSelective(@Param("record") App record, @Param("example") AppExample example);

    int updateByExample(@Param("record") App record, @Param("example") AppExample example);
}