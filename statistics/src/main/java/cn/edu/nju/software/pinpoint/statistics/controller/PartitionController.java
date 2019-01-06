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
@RequestMapping(value = "/partition")
public class PartitionController {
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
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JSONResult findPartitionResultList(String dynamicInfoId, String algorithmsId,int type, Integer page, Integer pageSize) throws Exception {
        List<PartitionResult> partitionResults = partitionResultService.queryPartitionResultListPaged(dynamicInfoId,algorithmsId, type, page, pageSize);
        return JSONResult.ok(partitionResults);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "partitionId", value = "划分结果id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "type", value = "结点类型，0-类结点，1-方法结点", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "page", value = "分页：页码",  dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "分页：每页大小（默认大小100）",  dataType = "int")
    })
    @ApiOperation(value = "划分结果详情分页列表", notes = "返回状态200成功")
    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public JSONResult findPartitionResultDetail(String partitionId, int type, Integer page, Integer pageSize) throws Exception {
        List<HashMap<String, String>> partitionResults = partitionDetailService.queryPartitionDetailListPaged(partitionId, type, page, pageSize);
        return JSONResult.ok(partitionResults);
    }

//    @ApiOperation(value = "划分结果详情分页列表2", notes = "返回状态200成功")
//    @ApiModelProperty(value = "param", notes = "param的json串")
//    @RequestMapping(value = "/details", method = RequestMethod.POST)
//    public JSONResult findPartitionResultDetail2(@RequestBody HashMap<String,Object> param) {
//        String partitionId = (String)param.get("partitionId");
//        int type =  Integer.valueOf((String) param.get("type"));
//        Integer page =Integer.valueOf((String)param.get("page"));
//        Integer pageSize = Integer.valueOf((String)param.get("pageSize"));
//        List<HashMap<String, String>> partitionResults = partitionDetailService.queryPartitionDetailListPaged(partitionId, type, page, pageSize);
//        return JSONResult.ok(partitionResults);
//    }
}
