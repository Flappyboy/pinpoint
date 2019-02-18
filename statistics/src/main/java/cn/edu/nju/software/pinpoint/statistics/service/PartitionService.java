package cn.edu.nju.software.pinpoint.statistics.service;

import cn.edu.nju.software.pinpoint.statistics.entity.PartitionInfo;
import cn.edu.nju.software.pinpoint.statistics.entity.bean.PartitionGraph;

import java.util.HashMap;
import java.util.List;

public interface PartitionService {
    PartitionInfo findPartitionById(String partitionId);
    public void addPartition(PartitionInfo partition);
    public void delPartition(String partitionInfoId);
    public void updatePartition(PartitionInfo partition);
    public List<HashMap<String ,Object>> findBycondition(Integer page, Integer pageSize, String appName, String desc, String algorithmsid, Integer type);
    public int count(String appName,String desc,String algorithmsid,Integer type) ;
    public PartitionGraph getGraph(String partitionInfoId);
}
