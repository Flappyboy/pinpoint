package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.dao.MethodNodeMapper;
import cn.edu.nju.software.pinpoint.statistics.entity.MethodNode;
import cn.edu.nju.software.pinpoint.statistics.entity.MethodNodeExample;
import cn.edu.nju.software.pinpoint.statistics.service.MethodNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MethodNodeServiceImpl implements MethodNodeService {
    @Autowired
    private MethodNodeMapper methodNodeMapper;

    @Override
    public MethodNode findById(String id) {
        MethodNode methodNode = new MethodNode();
        MethodNodeExample example = new MethodNodeExample();
        MethodNodeExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id).andFlagEqualTo(1);
        List<MethodNode> methodNodes = methodNodeMapper.selectByExample(example);
        if (methodNodes.size() > 0 && methodNodes != null)
            methodNode = methodNodes.get(0);
        return methodNode;
    }
}
