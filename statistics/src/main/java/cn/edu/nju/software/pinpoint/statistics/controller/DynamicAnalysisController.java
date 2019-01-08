package cn.edu.nju.software.pinpoint.statistics.controller;

import cn.edu.nju.software.pinpoint.statistics.entity.DynamicAnalysisInfo;
import cn.edu.nju.software.pinpoint.statistics.entity.common.JSONResult;
import cn.edu.nju.software.pinpoint.statistics.service.DynamicAnalysisInfoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Api(value = "动态分析信息接口")
@RequestMapping(value = "/dynaInfo")
public class DynamicAnalysisController {
    @Autowired
    private DynamicAnalysisInfoService dynamicAnalysisInfoService;

    @ApiModelProperty(value = "dynamicAnalysisInfo", notes = "动态分析信息的json串")
    @ApiOperation(value = "新增动态分析信息", notes = "返回状态200成功")
    @RequestMapping(value = "/addDynaInfo", method = RequestMethod.POST)
    public JSONResult addApp(@RequestBody DynamicAnalysisInfo dynamicAnalysisInfo) throws Exception {
        dynamicAnalysisInfoService.saveDAnalysisInfo(dynamicAnalysisInfo);
        return JSONResult.ok();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "page", value = "分页：页码",  dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "分页：每页大小（默认大小100）",  dataType = "int")
    })
    @ApiOperation(value = "分页查询动态分析信息", notes = "返回状态200成功")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JSONResult queryAppListPaged(Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            page = 100;
        }
        List<DynamicAnalysisInfo> mylist = dynamicAnalysisInfoService.queryDAnalysisInfoListPaged(page, pageSize);
        return JSONResult.ok(mylist);
    }
}
