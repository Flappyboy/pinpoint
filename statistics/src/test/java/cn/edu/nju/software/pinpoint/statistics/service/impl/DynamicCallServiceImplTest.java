package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.PinpointStatisticsApplication;
import cn.edu.nju.software.pinpoint.statistics.entity.DynamicAnalysisInfo;
import cn.edu.nju.software.pinpoint.statistics.service.DynamicCallService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = PinpointStatisticsApplication.class)
public class DynamicCallServiceImplTest {
    @Autowired
    DynamicCallService dynamicCallService;

    @Test
    public void statisticsPartitionResultEdge() {
        DynamicAnalysisInfo dynamicAnalysisInfo = new DynamicAnalysisInfo();
        dynamicAnalysisInfo.setId("1");
        dynamicAnalysisInfo.setAppid("1902168DCWHRK86W");
        //dynamicCallService.statisticsCallInfo(dynamicAnalysisInfo);
    }

}