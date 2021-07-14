package com.example.mystudydemo.config;

import java.util.ResourceBundle;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/3/31 13:06
 * @description: 读取配置文件
 */
public class SystemConfig {

    static String redis = "redis";

    public static String getConfigFileRedis(String itemIndex) {
        try {
            ResourceBundle resource = ResourceBundle.getBundle(redis);
            return resource.getString(itemIndex);
        } catch (Exception e) {
            return "";
        }
    }
}