package cn.edu.nju.software.algorithm.kmeans;

public class VNode {
    int ivex;               //顶点位置
    String data;          // 顶点信息
    ENode firstEdge;    // 指向第一条依附该顶点的弧
    int degree;
    int sumWeight;

    public int getSumWeight() {
        return sumWeight;
    }

    public void setSumWeight(int sumWeight) {
        this.sumWeight = sumWeight;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getIvex() {
        return ivex;
    }

    public String getData() {
        return data;
    }

    public ENode getFirstEdge() {
        return firstEdge;
    }

    public void setIvex(int ivex) {
        this.ivex = ivex;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setFirstEdge(ENode firstEdge) {
        this.firstEdge = firstEdge;
    }
}
