package cn.edu.nju.software.algorithm.kmeans;

public class EData {
    String start; // 边的起点
    String end;   // 边的终点
    int weight; // 边的权重

    public EData(String start, String end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}
