package cn.edu.nju.software.algorithm.kmeans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 图的边

@Setter
@Getter
@ToString
public class EData {
    String start; // 边的起点
    String end;   // 边的终点
    double weight; // 边的权重

    public EData() {
    }

    public EData(String start, String end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}
