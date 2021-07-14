package com.example.mystudydemo.enums;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/4/12 10:35
 * @description:
 */
public enum ExceptionEnum {

    BIZ_EXCEPTION(0,"业务异常"),

    DB_EXCEPTION(1,"数据库异常"),

    REDIS_EXCEPTION(2, "redis异常"),

    ES_EXCEPTION(3, "ES异常"),

    MQ_EXCEPTION(4, "消息队列异常"),

    SYS_EXCEPTION(5, "系统异常");

    int type;
    String description;

    ExceptionEnum(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
