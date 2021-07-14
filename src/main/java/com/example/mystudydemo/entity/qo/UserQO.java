package com.example.mystudydemo.entity.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/6/24 10:28
 * @description:
 */
@Data
public class UserQO {
    @ApiModelProperty(value = "当前页数", example = "1", required = true)
    private Long current;

    @ApiModelProperty(value = "每页记录数", example = "20", required = true)
    private Long size;

    @ApiModelProperty(value = "方向编号", example = "01")
    private List fxbh;

    @ApiModelProperty(value = "设备编号", example = "330783000000330783000860010002")
    private String sbbh;
}
