package cn.edu.nju.software.algorithm.kmeans;

import lombok.Getter;
import lombok.Setter;

// 邻接表中表对应的链表的顶点

@Setter
@Getter
public class ENode {
    int ivex;       // 该边所指向的顶点的位置
    String data;
    double weight;     // 该边的权
    ENode nextEdge; // 指向下一条弧的指针


}

