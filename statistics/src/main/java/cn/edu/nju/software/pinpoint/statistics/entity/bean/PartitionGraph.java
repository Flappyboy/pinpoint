package cn.edu.nju.software.pinpoint.statistics.entity.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PartitionGraph {
    private List<PartitionNode> partitionNodes;
    private List<PartitionEdge> partitionEdges;
}
