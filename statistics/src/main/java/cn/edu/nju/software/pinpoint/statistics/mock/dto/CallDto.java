package cn.edu.nju.software.pinpoint.statistics.mock.dto;

public class CallDto {
    private Long id;
    private Long CallerId;
    private String CallerName;
    private String CalleeName;
    private Integer count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCallerId() {
        return CallerId;
    }

    public void setCallerId(Long callerId) {
        CallerId = callerId;
    }

    public String getCallerName() {
        return CallerName;
    }

    public void setCallerName(String callerName) {
        CallerName = callerName;
    }

    public String getCalleeName() {
        return CalleeName;
    }

    public void setCalleeName(String calleeName) {
        CalleeName = calleeName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
