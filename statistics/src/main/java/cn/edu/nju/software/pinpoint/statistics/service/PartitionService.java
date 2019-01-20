package cn.edu.nju.software.pinpoint.statistics.service;

import cn.edu.nju.software.pinpoint.statistics.entity.Partition;

import java.util.List;

public interface PartitionService {
    public void addPartition(Partition partition);
    public List<Partition> findBycondition(Integer page, Integer pageSize,String appName,String desc);

}
