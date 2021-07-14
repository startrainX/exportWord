package com.example.mystudydemo.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/5/31 18:47
 * @description:
 */
@Data
@TableName("veh_passrec")
public class VehPassRec {
    /** 序列号：6位行政区划＋3位系统編 号+13位序列号（不足13位左补“0”） */
    @TableField("xlh")
    private String xlh;
    /** 设备编号 */
    @TableField("sbbh")
    private String sbbh;
    /** 方向编号 */
    @TableField("fxbh")
    private String fxbh;
    /** 号牌号码 */
    @TableField("hphm")
    private String hphm;
    /** 号牌种类 */
    @TableField("hpzl")
    private String hpzl;
    /** 车辆编号 */
    @TableField("clbh")
    private String clbh;
    /** 过车时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("gcsj")
    private Date gcsj;
    /** 车辆速度 */
    @TableField("clsd")
    private double clsd;
    /** 车外廓长 */
    @TableField("cwkc")
    private double cwkc;
    /** 号牌颜色 */
    @TableField("hpys")
    private String hpys;
    /** 车辆类型 */
    @TableField("cllx")
    private String cllx;
    /** 红灯亮起时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("hdlqsj")
    private Date hdlqsj;
    /** 红灯持续时间 */
    @TableField("hdcxsj")
    private double hdcxsj;
    /** 远景1图片 */
    @TableField("cltp1")
    private String cltp1;
    /** 远景2图片 */
    @TableField("cltp2")
    private String cltp2;
    /** 远景3图片 */
    @TableField("cltp3")
    private String cltp3;
    /** 人像图片 */
    @TableField("cltp5")
    private String cltp5;
    /** 号牌特写图片 */
    @TableField("cltp6")
    private String cltp6;
    /** 车辆图片7 */
    @TableField("cltp7")
    private String cltp7;
    /** 违法行为代码 */
    @TableField("wfxw")
    private String wfxw;
    /** 大车限速 */
    @TableField("dcxs")
    private String dcxs;
    /** 小车限速 */
    @TableField("xcxs")
    private String xcxs;
    /** 违法标记 0-非违法 1－违法 */
    @TableField("wfbj")
    private String wfbj;
    /** 数据上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("sjscsj")
    private Date sjscsj;
    /** 车道编号 */
    @TableField("cdbh")
    private String cdbh;
    /** 地点编号 */
    @TableField("ddbh")
    private String ddbh;
    /** 地点描述 */
    @TableField("ddms")
    private String ddms;
    /** 平台结点编号 */
    @TableField("ptjdbh")
    private String ptjdbh;
    /** 上传状态 0-无需上传 1-未上传 2－已上传 */
    @TableField("sczt")
    private String sczt;
    /** 行驶方向 */
    @TableField("xsfx")
    private String xsfx;
    /** 方向描述 */
    @TableField("fxms")
    private String fxms;
    /** 小车限速 */
    @TableField("clpp")
    private String clpp;
    /** 证据存储方式 0-FTP(分布存储) 1-NAS 2-数据库 3-FTP(中心存储)9-无证据 */
    @TableField("zjccfs")
    private String zjccfs;
    /** 车身颜色 */
    @TableField("csys")
    private String csys;
    /** 备用字段1 */
    @TableField("byzd1")
    private String byzd1;
    /** 备用字段2 */
    @TableField("byzd2")
    private String byzd2;
    /** 备用字段3 */
    @TableField("byzd3")
    private String byzd3;

}
