package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.dao.ClassNodeMapper;
import cn.edu.nju.software.pinpoint.statistics.entity.ClassNode;
import cn.edu.nju.software.pinpoint.statistics.entity.ClassNodeExample;
import cn.edu.nju.software.pinpoint.statistics.service.ClassNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassNodeServiceImpl implements ClassNodeService {
    @Autowired
    private ClassNodeMapper classNodeMapper;

    @Override
    public ClassNode findById(String id) {
        ClassNode classnode = new ClassNode();
        ClassNodeExample classNodeExample = new ClassNodeExample();
        ClassNodeExample.Criteria classNodeCriteria = classNodeExample.createCriteria();
        classNodeCriteria.andIdEqualTo(id).andFlagEqualTo(1);
        List<ClassNode> calleeNodes = classNodeMapper.selectByExample(classNodeExample);
        if (calleeNodes.size() > 0 && calleeNodes != null)
            classnode = calleeNodes.get(0);
        return classnode;
    }
}
