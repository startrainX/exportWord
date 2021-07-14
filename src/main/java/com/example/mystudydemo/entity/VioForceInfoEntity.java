package com.example.mystudydemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/5/26 21:34
 * @description:
 */
@Data
@TableName(value = "vioforceinfo")
@ApiModel(value = "VioForceInfoEntity对象", description = "用户表实体类")
public class VioForceInfoEntity {
    @ApiModelProperty(value = "用户名")
    @TableField(value = "pzbh")
    private String pzbh;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "wfxw1")
    private String wfxw1;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "wfsj")
    private String wfsj;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "cjbj")
    private String cjbj;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "fxjg")
    private String fxjg;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "wfxw")
    private String wfxw;

    @ApiModelProperty(value = "用户名")
    @TableField(value = "time")
    private String time;
}
