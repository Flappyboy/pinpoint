package cn.edu.nju.software.pinpoint.statistics.service;

import cn.edu.nju.software.pinpoint.statistics.entity.MethodNode;

import java.util.List;

public interface MethodNodeService {
    public MethodNode findById(String id);
    public List<MethodNode> findByCondition(String name,String classname,String appid);
}
