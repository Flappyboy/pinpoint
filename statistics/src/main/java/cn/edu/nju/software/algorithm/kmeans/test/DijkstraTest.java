package cn.edu.nju.software.algorithm.kmeans.test;

import cn.edu.nju.software.algorithm.kmeans.EData;
import cn.edu.nju.software.algorithm.kmeans.GraphUtil;

public class DijkstraTest {
    public static void main(String[] args) {
        String[] vexs = {"Aa", "Bb", "Cc", "Dd"};
        EData[] edges = {
                // 起点 终点 权
                new EData("Aa", "Bb", 12),
                new EData("Aa", "Cc", 4),
                new EData("Bb", "Dd", 3),
                new EData("Cc", "Dd", 1)
        };
        GraphUtil pG;

        // 采用已有的"图"
        pG = new GraphUtil(vexs, edges);

        int[] prev = new int[vexs.length];
        double[] dist = new double[vexs.length];


        // dijkstra算法获取"第4个顶点"到其它各个顶点的最短距离
        pG.dijkstra("Cc", prev, dist);

    }
}
