package cn.edu.nju.software.algorithm.mstcluster;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class NodeWeightPair {
    private ClassNode node;
    private double weight;

    public NodeWeightPair(ClassNode node, double weight) {
        this.node = node;
        this.weight = weight;
    }
}
