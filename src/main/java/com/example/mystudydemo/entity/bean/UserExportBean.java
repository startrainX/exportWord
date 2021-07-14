package com.example.mystudydemo.entity.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/5/26 15:14
 * @description:
 */
@Data
public class UserExportBean {

    @Excel(name = "id", width = 30, orderNum = "0")
    private Long id;

    @Excel(name = "username", width = 30, orderNum = "1")
    private String username;

    @Excel(name = "password", width = 30, orderNum = "2")
    private String password;

    @Excel(name = "realName", width = 30, orderNum = "3")
    private String realName;

    @Excel(name = "createTime", width = 30, orderNum = "4")
    private Date createTime;

    @Excel(name = "updateTime", width = 30, orderNum = "5")
    private Date updateTime;
}
