package cn.edu.nju.software.pinpoint.statistics.mock.dto;

import cn.edu.nju.software.pinpoint.statistics.entity.ClassNode;
import cn.edu.nju.software.pinpoint.statistics.utils.NameUtil;

public class ClassDto {
    private String id;
    private String name;
    private String packageName;
    private String simpleName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackageName() {
        return NameUtil.getPackageNameFromClassName(name);
    }

    public String getSimpleName() {
        return NameUtil.getSimpleNameFromClassName(name);
    }

}
