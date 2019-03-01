package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.PinpointStatisticsApplication;
import cn.edu.nju.software.pinpoint.statistics.entity.DynamicAnalysisInfo;
import cn.edu.nju.software.pinpoint.statistics.entity.Git;
import cn.edu.nju.software.pinpoint.statistics.service.GitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = PinpointStatisticsApplication.class)
public class GitServiceImplTest {

    @Autowired
    GitService gitService;

    @Test
    public void statisticsPartitionResultEdge() {
        List<Git> gitList = gitService.queryGitByAppId("190216G9CMGFD680");
        System.out.println(gitList.get(0));
    }

}