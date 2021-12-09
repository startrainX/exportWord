package com.example.mystudydemo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.example.mystudydemo.entity.ResultEntity;
import com.example.mystudydemo.entity.bean.VioForceInfoExportBean;
import com.example.mystudydemo.entity.po.ImportPersonPO;
import com.example.mystudydemo.mapper.UpmsPersonImportMapper;
import com.example.mystudydemo.service.VioForceInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Map<String, String> depts = new HashMap<>();
    @Autowired
    UpmsPersonImportMapper upmsPersonImportDao;

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

    @PostMapping(value = "/batchImport")
    @ApiParam(value = "上传的文件", required = true)
    @ApiOperation(value = "批量导入", notes = "批量导入")
    public ResultEntity<Map> batchImport(@RequestParam("file") MultipartFile file) {
        int successCount = 0;
        int index = 0;
        Map<String, String> map = new HashMap<>();
        try {
            Workbook wb = null;
            wb = new XSSFWorkbook(file.getInputStream());
            //获得所有sheet页
            int sheetNum = wb.getNumberOfSheets();
            if (sheetNum <= 0) {
                return ResultEntity.error("sheet页为空");
            }

            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
                // 如果行数少于两行则为没数据
                if (row.getRowNum() < 1) {
                    continue;
                }
                index = row.getRowNum();
                for (Cell cell : row) {
                    // 读取数据前设置单元格类型
                    cell.setCellType(CellType.STRING);
                }

                // 姓名，警号，手机号，部门必填，身份证号非必填
                String xm = row.getCell(0).getStringCellValue();
                String jybh = row.getCell(1).getStringCellValue();
                String sjh = row.getCell(2).getStringCellValue();
                String ssbm = row.getCell(3).getStringCellValue();
//                    String sfzh = row.getCell(4).getStringCellValue();
                if (StringUtils.isBlank(xm)) {
                    map.put("失败原因", "第" + row.getRowNum() + "行姓名为空，请添加后再试");
                }
                if (StringUtils.isBlank(jybh)) {
                    map.put("失败原因", "第" + row.getRowNum() + "行警员编号为空，请添加后再试");
                    continue;
                }
                if (StringUtils.isBlank(sjh)) {
                    map.put("失败原因", "第" + row.getRowNum() + "行手机号为空，请添加后再试");
                    continue;
                }
                if (!ContainDept(ssbm)) {
                    map.put("失败原因", "第" + row.getRowNum() + "行部门不存在，请稍后再试");
                    continue;
                }
                ImportPersonPO po = new ImportPersonPO(xm, sjh, jybh);
                Long person = upmsPersonImportDao.insertPerson(po);
                po.setPerson(po.getPerson());
                po.setSsbm(depts.get(ssbm));
                po.setPassword("$2a$10$Gh6bjf88SjoPxJwJu87XJuN7GAEP9ELiOYMMM5GGZ8r7ZOKueYpVC");
                upmsPersonImportDao.insertDeptPerson(po);
                upmsPersonImportDao.insertUser(po);
                successCount++;
            }
        } catch (Exception e) {
            map.put("失败原因", "第" + index + "行警号重复，请确认后再试");
            e.printStackTrace();
        } finally {
            map.put("成功总数：", String.valueOf(successCount));
            depts.clear();
            return ResultEntity.success("导入成功", map);
        }
    }

    private boolean ContainDept(String ssbm) {
        if (depts.isEmpty()) {
            List<Map<String, String>> list = upmsPersonImportDao.getDepts();
            for (Map<String, String> map : list) {
                depts.put(map.get("name"), map.get("code"));
            }
            return depts.containsKey(ssbm);
        } else {
            return depts.containsKey(ssbm);
        }
    }
}
