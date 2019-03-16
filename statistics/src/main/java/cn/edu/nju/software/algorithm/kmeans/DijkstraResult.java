package cn.edu.nju.software.algorithm.kmeans;

public class DijkstraResult {
    private String sourceData;
    private int sourceId;
    private String targetData;
    private int targetId;
    private int weigth;

    public void setSourceData(String sourceData) {
        this.sourceData = sourceData;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public void setTargetData(String targetData) {
        this.targetData = targetData;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getSourceData() {
        return sourceData;
    }

    public int getSourceId() {
        return sourceId;
    }

    public String getTargetData() {
        return targetData;
    }

    public int getTargetId() {
        return targetId;
    }

    public int getWeigth() {
        return weigth;
    }

    public void setWeigth(int weigth) {
        this.weigth = weigth;
    }
}
