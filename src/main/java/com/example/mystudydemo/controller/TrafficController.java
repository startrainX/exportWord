package com.example.mystudydemo.controller;

import com.example.mystudydemo.service.TrafficService;
import com.example.mystudydemo.utils.export.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.text.ParseException;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/6/8 9:40
 * @description:
 */
@RestController
@RequestMapping("/testExport")
@ResponseBody
@Api(value = "测试导出")
public class TrafficController {
    @Autowired
    TrafficService trafficService;

    @GetMapping("/ee")
    @ApiOperation(value = "导出报表",notes = "导出报表")
    private void ee(String startTime, String endTime, HttpServletResponse response, HttpServletRequest request) throws FileNotFoundException, InvalidFormatException, ParseException {

        String wordPath = trafficService.export(startTime,endTime);
        try {

            response.setCharacterEncoding("iso-8859-1");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, wordPath.substring(wordPath.indexOf("/")+1)));
            FileUtils.writeBytes(wordPath, response.getOutputStream());
            FileUtils.deleteFile(wordPath);
        } catch (Exception e) {
        }
    }

}
