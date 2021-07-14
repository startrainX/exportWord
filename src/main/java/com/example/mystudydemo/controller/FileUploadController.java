package com.example.mystudydemo.controller;

import com.example.mystudydemo.entity.ResultEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/7/10 16:40
 * @description:
 */
@Slf4j
@ResponseBody
@RestController
@RequestMapping("/fileUpload")
@Api(value = "文件上传")
public class FileUploadController {


    @ApiOperation(value="文件上传", notes="文件上传")
    @PostMapping(value = "/upload")
    public ResultEntity upload(@ApiParam(value = "上传的文件" ,required = true) @RequestParam("file") MultipartFile file) throws FileNotFoundException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        if (file.isEmpty()) {
            return ResultEntity.error("上传文件不能为空");
        }
        String fileName = file.getOriginalFilename();
        log.info("OriginalFilename是{}",fileName);
        fileName =sdf.format(new Date()) + fileName;
        File classPath = new ApplicationHome(getClass()).getSource();
        String filePath = classPath.getParentFile().getPath() + File.separator + "static"+File.separator;

        File fileN = new File(filePath);
        if (!fileN.exists()) {
            fileN.mkdir();
        }
        log.info("附件filePath存放位置为"+filePath);
        File dest = new File(filePath+fileName);
        try {
            file.transferTo(dest);
            log.info("上传成功"+fileName);
            return ResultEntity.success(fileName);
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
        return ResultEntity.error("上传失败！");
    }
}
