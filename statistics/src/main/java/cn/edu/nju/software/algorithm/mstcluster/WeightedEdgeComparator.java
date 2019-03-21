package cn.edu.nju.software.algorithm.mstcluster;

import java.util.Comparator;

public class WeightedEdgeComparator implements Comparator<WeightedEdge> {
    @Override
    public int compare(WeightedEdge o1, WeightedEdge o2){
        return new Double(o1.getScore()).compareTo(o2.getScore());
    }
}
