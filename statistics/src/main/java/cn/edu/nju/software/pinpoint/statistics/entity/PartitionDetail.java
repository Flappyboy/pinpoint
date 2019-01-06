package cn.edu.nju.software.pinpoint.statistics.entity;

import java.io.Serializable;
import java.util.Date;

public class PartitionDetail implements Serializable {
    private String id;

    private String nodeid;

    private String patitionresultid;

    private Date createdat;

    private Date updatedat;

    private Integer type;

    private Integer flag;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid == null ? null : nodeid.trim();
    }

    public String getPatitionresultid() {
        return patitionresultid;
    }

    public void setPatitionresultid(String patitionresultid) {
        this.patitionresultid = patitionresultid == null ? null : patitionresultid.trim();
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", nodeid=").append(nodeid);
        sb.append(", patitionresultid=").append(patitionresultid);
        sb.append(", createdat=").append(createdat);
        sb.append(", updatedat=").append(updatedat);
        sb.append(", type=").append(type);
        sb.append(", flag=").append(flag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}