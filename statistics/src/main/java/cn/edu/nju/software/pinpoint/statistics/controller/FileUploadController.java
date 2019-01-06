package cn.edu.nju.software.pinpoint.statistics.controller;

import cn.edu.nju.software.pinpoint.statistics.entity.common.JSONResult;
import cn.edu.nju.software.pinpoint.statistics.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
//@RestController
@Api(value = "文件上传接口")
@Controller
public class FileUploadController {
    /*
     * 获取file.html页面
     */
    @RequestMapping("file")
    public String file(){
        return "/file";
    }

    //处理文件上传
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "文件上传", notes = "返回状态200成功")
    @ResponseBody
    public JSONResult uploadImg(@RequestParam("file") MultipartFile file,
                                HttpServletRequest request) {

        String contentType = file.getContentType();   //图片文件类型
        String fileName = file.getOriginalFilename();  //图片名字

        //文件存放路径

        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//        String filePath = "/Users/yaya/Desktop/upload/";
        String filePath = path+"/upload/";
        System.out.println("======:   "+path);

        //调用文件处理类FileUtil，处理文件，将文件写入指定位置
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
        }

        Map<String, String> result = new HashMap<String, String>();
        result.put("path", filePath + fileName);
        // 返回图片的存放路径
        return JSONResult.ok(result);
    }
}
