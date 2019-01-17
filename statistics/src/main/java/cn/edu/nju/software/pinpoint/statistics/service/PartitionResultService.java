package cn.edu.nju.software.pinpoint.statistics.service;

import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResult;

import java.io.IOException;
import java.util.List;

public interface PartitionResultService {
    public PartitionResult savePartitionResult(PartitionResult partitionResult);

    public void updatePartitionResult(PartitionResult partitionResult);

    public void deletePartitionResult(String partitionResultId);

    public PartitionResult queryPartitionResultById(String partitionResultId);

    public List<PartitionResult> queryPartitionResultListPaged(String dynamicInfoId,String algorithmsId,int type,Integer page, Integer pageSize);

    public void partition(String appid,String algorithmsid,String dynamicanalysisinfoid,int type) throws IOException;

}
