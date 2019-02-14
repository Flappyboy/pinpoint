package cn.edu.nju.software.pinpoint.statistics.mock.dto;

public class EdgeDto {

    private String source;
    private String target;

    public EdgeDto(String source, String target) {
        this.source = source;
        this.target = target;
    }

    public EdgeDto() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
