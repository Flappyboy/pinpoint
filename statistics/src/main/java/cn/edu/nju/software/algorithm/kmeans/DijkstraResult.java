package cn.edu.nju.software.algorithm.kmeans;

import lombok.Getter;
import lombok.Setter;
//存放最短路径的实体
@Setter
@Getter
public class DijkstraResult {
    private String sourceData;
    private int sourceId;
    private String targetData;
    private int targetId;
    private double weigth;
}
