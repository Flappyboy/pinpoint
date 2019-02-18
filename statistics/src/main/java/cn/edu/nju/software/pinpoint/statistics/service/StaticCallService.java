package cn.edu.nju.software.pinpoint.statistics.service;

import cn.edu.nju.software.pinpoint.statistics.entity.StaticCallInfo;

import java.util.HashMap;
import java.util.List;

public interface StaticCallService {

    StaticCallInfo queryCallById(String id);

    public void saveStaticAnalysis(String appid,String path,Integer flag) throws Exception;
    public List<HashMap<String,String>> findEdgeByAppId(String appid,int page,int pageSize,int type);
    public int countOfStaticAnalysis(String appid,int type);
}
