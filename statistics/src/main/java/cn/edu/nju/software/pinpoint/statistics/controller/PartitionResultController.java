package cn.edu.nju.software.pinpoint.statistics.controller;

import cn.edu.nju.software.pinpoint.statistics.entity.PartitionResult;
import cn.edu.nju.software.pinpoint.statistics.entity.common.JSONResult;
import cn.edu.nju.software.pinpoint.statistics.service.PartitionDetailService;
import cn.edu.nju.software.pinpoint.statistics.service.PartitionResultService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@Api(value = "划分结果接口")
@RequestMapping(value = "/api")
public class PartitionResultController {
    @Autowired
    private PartitionResultService partitionResultService;
    @Autowired
    private PartitionDetailService partitionDetailService;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "dynamicInfoId", value = "动态信息id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "algorithmsId", value = "算法id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "type", value = "结点类型，0-类结点，1-方法结点", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "page", value = "分页：页码",  dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "分页：每页大小（默认大小100）",  dataType = "int")
    })
    @ApiOperation(value = "划分结果分页列表", notes = "返回状态200成功")
    @RequestMapping(value = "/partitionResult", method = RequestMethod.GET)
    public JSONResult findPartitionResultList(String dynamicInfoId, String algorithmsId,int type, Integer page, Integer pageSize) throws Exception {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 100;
        }
        List<PartitionResult> partitionResults = partitionResultService.queryPartitionResultListPaged(dynamicInfoId,algorithmsId, type, page, pageSize);
        int count = partitionResultService.countOfPartitionResult(dynamicInfoId,algorithmsId, type);
        HashMap<String ,Object> result = new HashMap<String ,Object>();
        result.put("list",partitionResults);
        result.put("total",count);
        return JSONResult.ok(result);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "partitionId", value = "划分结果id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "type", value = "结点类型，0-类结点，1-方法结点", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "page", value = "分页：页码",  dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "分页：每页大小（默认大小100）",  dataType = "int")
    })
    @ApiOperation(value = "划分结果详情分页列表", notes = "返回状态200成功")
    @RequestMapping(value = "/partitionResult/{id}", method = RequestMethod.GET)
    public JSONResult findPartitionResultDetail(@PathVariable String id, int type, Integer page, Integer pageSize) throws Exception {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 100;
        }
        List<HashMap<String, String>> partitionDetails = partitionDetailService.queryPartitionDetailListPaged(id, type, page, pageSize);
        int count = partitionDetailService.countOfPartitionDetail(id, type);
        HashMap<String ,Object> result = new HashMap<String ,Object>();
        result.put("list",partitionDetails);
        result.put("total",count);
        return JSONResult.ok(result);
    }

    @RequestMapping(value = "/partitionResult/do", method = RequestMethod.GET)
    public JSONResult doPartition(String appid,String algorithmsid,String dynamicanalysisinfoid,int type,String partitionId) throws Exception {
        partitionResultService.partition(appid,algorithmsid,dynamicanalysisinfoid,type,partitionId);
        return JSONResult.ok();
    }
}
