package cn.edu.nju.software.pinpoint.statistics.controller;

import cn.edu.nju.software.pinpoint.statistics.entity.App;
import cn.edu.nju.software.pinpoint.statistics.entity.common.JSONResult;
import cn.edu.nju.software.pinpoint.statistics.service.AppService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Api(value = "项目App相关接口")
@RequestMapping(value = "/app")
public class AppController {
    @Autowired
    private AppService appService;

    @ApiModelProperty(value = "app", notes = "项目信息的json串")
    @ApiOperation(value = "新增项目", notes = "返回状态200成功")
    @RequestMapping(value = "/addApp", method = RequestMethod.POST)
    public JSONResult addApp(@RequestBody App app) throws Exception {
        appService.saveApp(app);
        return JSONResult.ok();
    }

    @ApiModelProperty(value = "app", notes = "项目信息的json串")
    @ApiOperation(value = "更新项目", notes = "返回状态200成功")
    @RequestMapping(value = "/updateApp", method = RequestMethod.POST)
    public JSONResult updateApp(@RequestBody App app) throws Exception {
        appService.updateApp(app);
        return JSONResult.ok();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "项目appId", required = true, dataType = "String"),
    })
    @ApiOperation(value = "删除项目", notes = "返回状态200成功")
    @RequestMapping(value = "/deleteApp", method = RequestMethod.GET)
    public JSONResult deleteApp(String id) throws Exception {
        appService.deleteApp(id);
        return JSONResult.ok();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "项目appId", required = true, dataType = "String"),
    })
    @ApiOperation(value = "根据id查询项目", notes = "返回状态200成功")
    @RequestMapping(value = "/app", method = RequestMethod.GET)
    public JSONResult queryAppById(String id) throws Exception {
        App app = appService.queryAppById(id);
        return JSONResult.ok(app);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "page", value = "分页：页码",  dataType = "int"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "分页：每页大小（默认大小100）",  dataType = "int")
    })
    @ApiOperation(value = "分页查询项目列表", notes = "返回状态200成功")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public JSONResult queryAppListPaged(Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 100;
        }
        List<App> appList = appService.queryUserListPaged(page, pageSize);
        return JSONResult.ok(appList);
    }

}
