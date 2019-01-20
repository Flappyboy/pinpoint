package cn.edu.nju.software.pinpoint.statistics.service;

import cn.edu.nju.software.pinpoint.statistics.entity.PartitionInfo;

import java.util.HashMap;
import java.util.List;

public interface PartitionService {
    public void addPartition(PartitionInfo partition);
    public void delPartition(String id);
    public void updatePartition(PartitionInfo partition);
    public List<HashMap<String ,Object>> findBycondition(Integer page, Integer pageSize, String appName, String desc, String algorithmsid, Integer type);
    public int count(String appName,String desc,String algorithmsid,Integer type) ;
}
