package com.example.mystudydemo.entity;

import com.example.mystudydemo.enums.ExceptionEnum;
import lombok.Data;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/4/12 10:35
 * @description:
 */
public class ResultEntity<T> {

    private boolean status;

    private String message;

    private Integer errorType;

    private T data;

    public ResultEntity() {
    }

    public ResultEntity(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResultEntity(boolean status, String message, Integer errorType, T data) {
        this.status = status;
        this.message = message;
        this.errorType = errorType;
        this.data = data;
    }

    public static <T> ResultEntity<T> success() {
        return new ResultEntity<>(true,null,null);
    }

    public static <T> ResultEntity<T> success(String message) {
        return new ResultEntity<>(true, message, null);
    }

    public static <T> ResultEntity<T> success(String message, T data) {
        return new ResultEntity<>(true, message, data);
    }

    public static <T> ResultEntity<T> success(String message, Object args, T data) {
        return new ResultEntity<>(true, String.format(message, args), data);
    }

    public static <T> ResultEntity<T> error(String message) {
        return new ResultEntity<>(false, message, ExceptionEnum.BIZ_EXCEPTION.getType(),null);
    }

    public static <T> ResultEntity<T> error(String message, int errorType) {
        return new ResultEntity<>(false, message, errorType,null);
    }

    public static <T> ResultEntity<T> error(String message, Object args) {
        return new ResultEntity<>(false, String.format(message, args), ExceptionEnum.BIZ_EXCEPTION.getType(), null);
    }

    public static <T> ResultEntity<T> error(String message, Object args, int errorType) {
        return new ResultEntity<>(false, String.format(message, args), errorType, null);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorType() {
        return errorType;
    }

    public void setErrorType(Integer errorType) {
        this.errorType = errorType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
