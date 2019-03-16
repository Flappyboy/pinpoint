package cn.edu.nju.software.algorithm.kmeans;

public class ENode {
    int ivex;       // 该边所指向的顶点的位置
    String data;
    int weight;     // 该边的权
    ENode nextEdge; // 指向下一条弧的指针

    @Override
    public String toString() {
        return "ENode{" +
                "ivex=" + ivex +
                ", data='" + data + '\'' +
                ", weight=" + weight +
                ", nextEdge=" + nextEdge +
                '}';
    }

    public void setIvex(int ivex) {
        this.ivex = ivex;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setNextEdge(ENode nextEdge) {
        this.nextEdge = nextEdge;
    }

    public int getIvex() {
        return ivex;
    }

    public String getData() {
        return data;
    }

    public int getWeight() {
        return weight;
    }

    public ENode getNextEdge() {
        return nextEdge;
    }
}

