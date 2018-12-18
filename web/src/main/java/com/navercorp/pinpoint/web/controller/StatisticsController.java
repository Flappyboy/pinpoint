package com.navercorp.pinpoint.web.controller;

import com.navercorp.pinpoint.common.util.TransactionId;
import com.navercorp.pinpoint.web.calltree.span.CallTreeIterator;
import com.navercorp.pinpoint.web.filter.Filter;
import com.navercorp.pinpoint.web.filter.FilterBuilder;
import com.navercorp.pinpoint.web.filter.FilterChain;
import com.navercorp.pinpoint.web.service.*;
import com.navercorp.pinpoint.web.statistics.Statistics;
import com.navercorp.pinpoint.web.view.TransactionMetaDataViewModel;
import com.navercorp.pinpoint.web.vo.LimitedScanResult;
import com.navercorp.pinpoint.web.vo.callstacks.RecordSet;
import com.navercorp.pinpoint.web.vo.scatter.Dot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class StatisticsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ScatterChartService scatter;

    @Autowired
    private SpanService spanService;

    @Autowired
    private TransactionInfoService transactionInfoService;

    @Autowired
    private FilteredMapService filteredMapService;

    @Autowired
    private FilteredMapService flow;

    @Autowired
    private FilterBuilder filterBuilder;

    private static final String PREFIX_TRANSACTION_ID = "I";
    private static final String PREFIX_TIME = "T";
    private static final String PREFIX_RESPONSE_TIME = "R";
    /**
     * selected points from scatter chart data query
     *
     * @param requestParam
     * @return
     */
    @RequestMapping(value = "/statistcsallcall")
    @ResponseBody
    public TransactionMetaDataViewModel statistcsallcall(@RequestParam Map<String, String> requestParam) {
        TransactionMetaDataViewModel viewModel = new TransactionMetaDataViewModel();
        String an = requestParam.get("an");//"jpetstore"
        LimitedScanResult<List<TransactionId>> limitedScanResult = flow.selectTraceIdsFromApplicationTraceIndex(an);
        Filter filter = new FilterChain();
        List<Dot> dotList = scatter.selectScatterData(limitedScanResult.getScanData(),an,filter);
        List<SpanResult> spanResultList =  spanService.selectSpans(dotList);
        Statistics statistics = new Statistics(requestParam.get("pn"));
        for (int i=0; i<spanResultList.size(); i++){
            SpanResult spanResult = spanResultList.get(i);
            final CallTreeIterator callTreeIterator = spanResult.getCallTree();
            RecordSet recordSet = this.transactionInfoService.createRecordSet(callTreeIterator, dotList.get(i).getAcceptedTime(), dotList.get(i).getAgentId(), -1);
            statistics.statisticsRecord(recordSet);
        }
        statistics.saveClass();
        statistics.saveMethod();
        System.out.println("statisc");

        return viewModel;
    }
    @RequestMapping(value = "/statistics/{page}")
    @ResponseBody
    public ModelAndView statistics(@PathVariable(value="page") String page, @RequestParam Map<String, String> requestParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(page);

        return modelAndView;
    }

    @RequestMapping(value = "/querystatistics")
    @ResponseBody
    public List<Statistics.Call> queryStatistics(@RequestParam Map<String, String> requestParam) {
        List<Statistics.Call> callList = new ArrayList<>();
        try {
            FileReader fr = new FileReader("E:\\workspace\\project\\pinpoint\\statics-method-com.hand.hap.txt");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                String[] strArray = str.split(",");
                if (strArray.length!=3)
                    continue;
                Statistics statistics = new Statistics("");
                Statistics.Call call = statistics.new Call( statistics.new Method(strArray[0]), statistics.new Method(strArray[1]), Long.parseLong(strArray[2]));
                callList.add(call);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return callList;
    }
}
