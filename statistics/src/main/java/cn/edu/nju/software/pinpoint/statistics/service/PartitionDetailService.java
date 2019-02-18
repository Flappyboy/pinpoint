package cn.edu.nju.software.pinpoint.statistics.service;

import cn.edu.nju.software.pinpoint.statistics.entity.PartitionDetail;

import java.util.HashMap;
import java.util.List;

public interface PartitionDetailService {
    public PartitionDetail savePartitionDetail(PartitionDetail partitionDetail);

    public void updatePartitionDetail(PartitionDetail partitionDetail);

    public void deletePartitionDetail(String partitionDetailId);

    public PartitionDetail queryPartitionDetailById(String partitionDetailId);

    public List<HashMap<String, String>> queryPartitionDetailListPaged(String partitionResultId, int type, Integer page, Integer pageSize);

    public int countOfPartitionDetail(String partitionResultId, int type);

    int countOfPartitionDetail(String partitionResultId);

    List<Object> queryPartitionDetailByResultId(String partitionResultId, Integer page, Integer pageSize);
    int countOfPartitionDetailByResultId(String partitionResultId);
}
