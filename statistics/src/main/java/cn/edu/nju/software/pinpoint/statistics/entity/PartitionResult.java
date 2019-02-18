package cn.edu.nju.software.pinpoint.statistics.entity;

import java.io.Serializable;
import java.util.Date;

public class PartitionResult implements Serializable {
    private String id;

    private String desc;

    private String name;

    private String algorithmsid;

    private String dynamicanalysisinfoid;

    private String appid;

    private Date createdat;

    private Date updatedat;

    private Integer flag;

    private Integer type;

    private String typeName;

    private Integer order;

    private String partitionid;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAlgorithmsid() {
        return algorithmsid;
    }

    public void setAlgorithmsid(String algorithmsid) {
        this.algorithmsid = algorithmsid == null ? null : algorithmsid.trim();
    }

    public String getDynamicanalysisinfoid() {
        return dynamicanalysisinfoid;
    }

    public void setDynamicanalysisinfoid(String dynamicanalysisinfoid) {
        this.dynamicanalysisinfoid = dynamicanalysisinfoid == null ? null : dynamicanalysisinfoid.trim();
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getPartitionid() {
        return partitionid;
    }

    public void setPartitionid(String partitionid) {
        this.partitionid = partitionid == null ? null : partitionid.trim();
    }

    public String getTypeName() {
        if(type==0)
            return "Class";
        else
            return  "Method";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", desc=").append(desc);
        sb.append(", name=").append(name);
        sb.append(", algorithmsid=").append(algorithmsid);
        sb.append(", dynamicanalysisinfoid=").append(dynamicanalysisinfoid);
        sb.append(", appid=").append(appid);
        sb.append(", createdat=").append(createdat);
        sb.append(", updatedat=").append(updatedat);
        sb.append(", flag=").append(flag);
        sb.append(", type=").append(type);
        sb.append(", order=").append(order);
        sb.append(", partitionid=").append(partitionid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}