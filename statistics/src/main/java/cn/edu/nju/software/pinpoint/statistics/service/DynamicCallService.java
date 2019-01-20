package cn.edu.nju.software.pinpoint.statistics.service;

import cn.edu.nju.software.pinpoint.statistics.entity.DynamicCallInfo;

import java.util.HashMap;
import java.util.List;

public interface DynamicCallService {
    public void saveDCallInfo(DynamicCallInfo dynamicCallInfo);

    public void updateDCallInfo(DynamicCallInfo dynamicCallInfo);

    public void deleteDCallInfo(String dynamicCallInfoId);

    public DynamicCallInfo queryDCallInfo(String dynamicCallInfoId);

    public List<DynamicCallInfo> queryDCallInfo(Integer page, Integer pageSize);
    public List<HashMap<String,String>> findEdgeByAppId(String dynamicAnalysisInfoId, int page, int pageSize, int type);
    public int countOfDynamicCall(String dynamicAnalysisInfoId,int type);
}
