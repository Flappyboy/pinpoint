package cn.edu.nju.software.pinpoint.statistics.service;

import cn.edu.nju.software.pinpoint.statistics.entity.PartitionDetail;

import java.util.HashMap;
import java.util.List;

public interface PartitionDetailService {
    public PartitionDetail savePartitionDetail(PartitionDetail partitionDetail);

    public void updatePartitionDetail(PartitionDetail partitionDetail);

    public void deletePartitionDetail(String partitionDetailId);

    public PartitionDetail queryPartitionDetailById(String partitionDetailId);

    public List<HashMap<String, String>> queryPartitionDetailListPaged(String partitionId, int type, Integer page, Integer pageSize);
}
