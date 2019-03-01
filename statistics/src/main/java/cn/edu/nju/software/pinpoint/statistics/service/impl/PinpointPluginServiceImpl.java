package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.plugin.Generate;
import cn.edu.nju.software.pinpoint.statistics.dao.AppMapper;
import cn.edu.nju.software.pinpoint.statistics.entity.App;
import cn.edu.nju.software.pinpoint.statistics.entity.AppExample;
import cn.edu.nju.software.pinpoint.statistics.entity.ClassNode;
import cn.edu.nju.software.pinpoint.statistics.service.AppService;
import cn.edu.nju.software.pinpoint.statistics.service.ClassNodeService;
import cn.edu.nju.software.pinpoint.statistics.service.PinpointPluginService;
import cn.edu.nju.software.pinpoint.statistics.service.StaticCallService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PinpointPluginServiceImpl implements PinpointPluginService {

    @Autowired
    ClassNodeService classNodeService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void generatePlugin(App app) {
        StringBuilder sb = new StringBuilder();
        List<ClassNode> classNodeList = classNodeService.findByAppid(app.getId());
        for (ClassNode classNode :
                classNodeList) {
            sb.append(classNode.getName());
        }
        Generate generate = new Generate(app.getName(), sb.toString(),app.getId());
        generate.generateJar();
    }
}
