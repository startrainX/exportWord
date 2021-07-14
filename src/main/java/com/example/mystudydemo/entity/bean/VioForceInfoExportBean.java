package com.example.mystudydemo.entity.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/5/27 9:26
 * @description:
 */
@Data
public class VioForceInfoExportBean {

    @Excel(name = "pzbh", width = 30, orderNum = "0")
    private String pzbh;

    @Excel(name = "wfxw1", width = 30, orderNum = "1")
    private String wfxw1;

    @Excel(name = "wfsj", width = 30, orderNum = "2")
    private String wfsj;

    @Excel(name = "cjbj", width = 30, orderNum = "3")
    private String cjbj;

    @Excel(name = "fxjg", width = 30, orderNum = "4")
    private String fxjg;

    @Excel(name = "code", width = 30, orderNum = "5")
    private String code;

    @Excel(name = "name", width = 30, orderNum = "6")
    private String name;

    @Excel(name = "wfxw", width = 30, orderNum = "7")
    private String wfxw;

    @Excel(name = "time", width = 30, orderNum = "8")
    private String time;
}
