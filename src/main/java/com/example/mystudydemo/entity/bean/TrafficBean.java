package com.example.mystudydemo.entity.bean;

import lombok.Data;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/6/7 15:18
 * @description:
 */
@Data
public class TrafficBean {
    private String areaName;
    private String roadName;
    private String avgIndex;
    private String avgSpeed;
    private String count;
    private String time;
}
