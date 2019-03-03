package cn.edu.nju.software.pinpoint.statistics.service;

import cn.edu.nju.software.pinpoint.statistics.entity.PartitionInfo;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResult;
import cn.edu.nju.software.pinpoint.statistics.mock.dto.GraphDto;

import java.io.IOException;
import java.util.List;

public interface PartitionResultService {
    public PartitionResult savePartitionResult(PartitionResult partitionResult);

    public void updatePartitionResult(PartitionResult partitionResult);

    public void deletePartitionResult(String partitionResultId);

    public PartitionResult queryPartitionResultById(String partitionResultId);

    public List<PartitionResult> queryPartitionResultListPaged(String dynamicInfoId,String algorithmsId,int type,Integer page, Integer pageSize);

    GraphDto queryPartitionResultForDto(String partitionId);

    List<PartitionResult> queryPartitionResult(String partitionId);

    public void partition(PartitionInfo partitionInfo) throws Exception;

    public int countOfPartitionResult(String dynamicInfoId,String algorithmsId,int type);

    int countOfPartitionResult(String partitionId);
}
