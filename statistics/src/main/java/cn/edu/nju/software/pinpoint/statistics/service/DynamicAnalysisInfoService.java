package cn.edu.nju.software.pinpoint.statistics.service;

import cn.edu.nju.software.pinpoint.statistics.entity.DynamicAnalysisInfo;

import java.util.List;

public interface DynamicAnalysisInfoService {
    public DynamicAnalysisInfo saveDAnalysisInfo(DynamicAnalysisInfo dAnalysisInfo);

    public void updateDAnalysisInfo(DynamicAnalysisInfo dAnalysisInfo);

    public void deleteDAnalysisInfo(String dAnalysisInfoId);

    public DynamicAnalysisInfo queryDAnalysisInfoById(String dAnalysisInfoId);

    public List<DynamicAnalysisInfo> queryDAnalysisInfoListPaged(Integer page, Integer pageSize,String appName,String desc);

    public int countOfDAnalysisInfo();

}
