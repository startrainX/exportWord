package com.example.mystudydemo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.example.mystudydemo.entity.bean.VioForceInfoExportBean;
import com.example.mystudydemo.service.VioForceInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/5/27 9:42
 * @description: 当需要返回html页面时，不能使用RestController和ResponseBody注解，会返回字符串而不是html页面
 */
@Controller
@RequestMapping("/export")
@Api(value = "export")
public class VioForceInfoController {

    @Autowired
    VioForceInfoService vioForceInfoService;

    @RequestMapping("/test")
    public String excel(){
        return "excel";
    }

    @ApiOperation(value = "导出信息接口（表格）", notes = "导出部门信息接口（表格）")
    @GetMapping(value = "/exportToExcel")
    @ResponseBody
    public void exportToExcel(String startTime, String endTime,HttpServletResponse response) {
        try {
            List<VioForceInfoExportBean> list = vioForceInfoService.searchDataExport(startTime, endTime);
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("部门信息","部门信息"),VioForceInfoExportBean.class,list);
            response.setHeader("Content-Disposition", "attachment;filename*= UTF-8''"+ URLEncoder.encode("部门信息.xls","UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
