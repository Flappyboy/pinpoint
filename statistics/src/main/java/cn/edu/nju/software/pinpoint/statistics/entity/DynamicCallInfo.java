package cn.edu.nju.software.pinpoint.statistics.entity;

import java.io.Serializable;
import java.util.Date;

public class DynamicCallInfo implements Serializable {
    private String id;

    private String caller;

    private String callee;

    private Integer count;

    private String dynamicanalysisinfoid;

    private Date createdat;

    private Date updatedat;

    private Integer isinclude;

    private Integer flag;

    private Integer type;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller == null ? null : caller.trim();
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee == null ? null : callee.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDynamicanalysisinfoid() {
        return dynamicanalysisinfoid;
    }

    public void setDynamicanalysisinfoid(String dynamicanalysisinfoid) {
        this.dynamicanalysisinfoid = dynamicanalysisinfoid == null ? null : dynamicanalysisinfoid.trim();
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public Date getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Date updatedat) {
        this.updatedat = updatedat;
    }

    public Integer getIsinclude() {
        return isinclude;
    }

    public void setIsinclude(Integer isinclude) {
        this.isinclude = isinclude;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", caller=").append(caller);
        sb.append(", callee=").append(callee);
        sb.append(", count=").append(count);
        sb.append(", dynamicanalysisinfoid=").append(dynamicanalysisinfoid);
        sb.append(", createdat=").append(createdat);
        sb.append(", updatedat=").append(updatedat);
        sb.append(", isinclude=").append(isinclude);
        sb.append(", flag=").append(flag);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}