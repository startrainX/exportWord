package com.example.mystudydemo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Objects;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/4/6 17:02
 * @description:
 */
@TableName(value = "user")
@ApiModel(value = "User对象", description = "用户表实体类")
public class User {

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "userName")
    private String userName;

    @ApiModelProperty(value = "密码")
    @TableField(value = "passWord")
    private String passWord;

    @ApiModelProperty(value = "真实姓名")
    @TableField(value = "realName")
    private String realName;

    @ApiModelProperty(value = "创建时间，example=2021-04-09 11:17:17")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "creatTime")
    private Date creatTime;

    @ApiModelProperty(value = "更新时间，example=2021-04-09 11:17:17")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "updateTime")
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && userName.equals(user.userName) && passWord.equals(user.passWord) && realName.equals(user.realName) && creatTime.equals(user.creatTime) && updateTime.equals(user.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, passWord, realName, creatTime, updateTime);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", realName='" + realName + '\'' +
                ", creatTime=" + creatTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
