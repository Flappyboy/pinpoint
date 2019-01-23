package cn.edu.nju.software.pinpoint.statistics.entity.bean;

import cn.edu.nju.software.pinpoint.statistics.entity.DynamicCallInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PartitionEdge {
    private String sourceCommunityId;
    private String targetCommunityId;
    private List<PartitionNodeEdge> edges;
}
