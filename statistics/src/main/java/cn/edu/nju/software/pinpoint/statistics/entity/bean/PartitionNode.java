package cn.edu.nju.software.pinpoint.statistics.entity.bean;

import cn.edu.nju.software.pinpoint.statistics.entity.ClassNode;
import cn.edu.nju.software.pinpoint.statistics.entity.MethodNode;
import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PartitionNode {
    private PartitionResult community;
    private List<ClassNode> classNodes;
    private List<MethodNode> methodNodes;
    private int claaSize = 0;
    private int methodSize = 0;

}
