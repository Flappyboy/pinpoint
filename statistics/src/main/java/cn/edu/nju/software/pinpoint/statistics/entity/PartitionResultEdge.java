package cn.edu.nju.software.pinpoint.statistics.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PartitionResultEdge implements Serializable {
    private String id;

    private String patitionresultaid;

    private String patitionresultbid;

    private String name;

    private String desc;

    private Date createdat;

    private Date updatedat;

    private List<StaticCallInfo> staticCallInfoList;

    private List<DynamicCallInfo> dynamicCallInfoList;

    private static final long serialVersionUID = 1L;

    public PartitionResultEdge() {
    }

    public PartitionResultEdge(String id, String patitionresultaid, String patitionresultbid, String name, String desc, Date createdat, Date updatedat) {
        this.id = id;
        this.patitionresultaid = patitionresultaid;
        this.patitionresultbid = patitionresultbid;
        this.name = name;
        this.desc = desc;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPatitionresultaid() {
        return patitionresultaid;
    }

    public void setPatitionresultaid(String patitionresultaid) {
        this.patitionresultaid = patitionresultaid == null ? null : patitionresultaid.trim();
    }

    public String getPatitionresultbid() {
        return patitionresultbid;
    }

    public void setPatitionresultbid(String patitionresultbid) {
        this.patitionresultbid = patitionresultbid == null ? null : patitionresultbid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
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

    public List<StaticCallInfo> getStaticCallInfoList() {
        return staticCallInfoList;
    }

    public void setStaticCallInfoList(List<StaticCallInfo> staticCallInfoList) {
        this.staticCallInfoList = staticCallInfoList;
    }

    public List<DynamicCallInfo> getDynamicCallInfoList() {
        return dynamicCallInfoList;
    }

    public void setDynamicCallInfoList(List<DynamicCallInfo> dynamicCallInfoList) {
        this.dynamicCallInfoList = dynamicCallInfoList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", patitionresultaid=").append(patitionresultaid);
        sb.append(", patitionresultbid=").append(patitionresultbid);
        sb.append(", name=").append(name);
        sb.append(", desc=").append(desc);
        sb.append(", createdat=").append(createdat);
        sb.append(", updatedat=").append(updatedat);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}