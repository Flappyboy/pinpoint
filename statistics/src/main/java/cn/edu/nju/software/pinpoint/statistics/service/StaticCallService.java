package cn.edu.nju.software.pinpoint.statistics.service;

import java.util.HashMap;
import java.util.List;

public interface StaticCallService {
    public void saveStaticAnalysis(String appid,String path,Integer flag) throws Exception;
    public List<HashMap<String,String>> findEdgeByAppId(String appid,int page,int pageSize,int type);
    public int countOfStaticAnalysis(String appid,int type);
}
