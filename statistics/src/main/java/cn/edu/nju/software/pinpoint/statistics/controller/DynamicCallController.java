package cn.edu.nju.software.pinpoint.statistics.controller;

import cn.edu.nju.software.pinpoint.statistics.entity.common.JSONResult;
import cn.edu.nju.software.pinpoint.statistics.service.DynamicCallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@Api(value = "动态调用关系接口")
public class DynamicCallController {

    @Autowired
    private DynamicCallService dynamicCallService;

    @ApiOperation(value = "动态结果分页列表", notes = "返回状态200成功")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "dynamicAnalysisInfoId", value = "动态信息id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "type", value = "结点类型，0-类结点，1-方法结点", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "page", value = "分页：页码",  dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "分页：每页大小（默认大小100）",  dataType = "int")
    })
    public JSONResult dynamicEdgeList(String dynamicAnalysisInfoId, Integer page, Integer pageSize, int type) throws Exception {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            page = 100;
        }

        List<HashMap<String, String>> data = dynamicCallService.findEdgeByAppId(dynamicAnalysisInfoId, page, pageSize, type);
        return JSONResult.ok(data);
    }
}
