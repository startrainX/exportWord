package com.example.mystudydemo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.mystudydemo.entity.ResultEntity;
import com.example.mystudydemo.entity.User;
import com.example.mystudydemo.entity.VehPassRec;
import com.example.mystudydemo.entity.bean.UserExportBean;
import com.example.mystudydemo.entity.qo.UserQO;
import com.example.mystudydemo.enums.ExceptionEnum;
import com.example.mystudydemo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/4/6 17:03
 * @description:
 */

@RestController
@RequestMapping("/testBoot")
@ResponseBody
@Api(value = "测试")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getUser/{id}")
    @ApiOperation(value = "getUser",notes = "根据id查询用户")
    public ResultEntity<User> getUser(@PathVariable int id){
        return ResultEntity.success("根据id查询用户成功", userService.selectById(id));
    }

    @GetMapping("getAll")
    @ApiOperation(value = "getAll",notes = "查询全部用户")
    public ResultEntity<List<User>> getAll() {
        return ResultEntity.success("查询全部用户成功", userService.getAll());
    }

    @PostMapping("insert")
    @ApiOperation(value = "insert",notes = "新增用户")
    public ResultEntity insert(@RequestBody User user) {
        user.setCreatTime(new Date());
        user.setUpdateTime(new Date());
        userService.insert(user);
        return ResultEntity.success("新增用户成功");
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(value = "delete",notes = "根据id删除用户")
    public ResultEntity delete(@PathVariable("id") Long id) {
        int b = userService.delete(id);
        if (b > 0) {
            return ResultEntity.success("删除用户成功");
        } else {
            return ResultEntity.error("删除用户失败", ExceptionEnum.SYS_EXCEPTION);
        }
    }

    @PutMapping("update")
    @ApiOperation(value = "update",notes = "根据id更新用户")
    public ResultEntity update(@RequestBody User user) {
        user.setUpdateTime(new Date());
        userService.update(user);
        return ResultEntity.success("更新用户成功");

    }

    @ApiOperation(value = "导出信息接口（表格）", notes = "导出部门信息接口（表格）")
    @GetMapping(value = "/exportToExcel")
    public void exportToExcel(HttpServletResponse response) {
        try {
            List<UserExportBean> list = userService.searchExcelData();
            list.sort(Comparator.comparing(UserExportBean::getId));
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("部门信息","部门信息"),UserExportBean.class,list);
            response.setHeader("Content-Disposition", "attachment;filename*= UTF-8''"+ URLEncoder.encode("部门信息"+".xls","UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param qo
     * @return
     */
    @GetMapping("getDataPage")
    @ApiOperation(value = "getDataPage",notes = "分页查询全部用户")
    public ResultEntity<IPage<User>> getDataPage(UserQO qo) {
        return ResultEntity.success("分页查询全部用户", userService.getDataPage(qo));
    }

    @GetMapping("testForEach")
    @ApiOperation(value = "testForEach",notes = "分页查询全部用户")
    public ResultEntity<IPage<VehPassRec>> testForEach(UserQO qo) {
        return ResultEntity.success("分页查询全部用户", userService.testForEach(qo));
    }

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        LocalDate localDate = now.minusDays(3);
        LocalDate s = localDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        System.out.println("---上周----" + localDate);
        System.out.println("---上周一----" + s);
    }
}