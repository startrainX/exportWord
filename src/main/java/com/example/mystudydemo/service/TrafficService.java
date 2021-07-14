package com.example.mystudydemo.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.FileNotFoundException;
import java.text.ParseException;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/6/8 9:23
 * @description:
 */
public interface TrafficService {
    /**
     * 导出报表
     * @param startTime
     * @param endTime
     * @return
     * @throws FileNotFoundException
     * @throws InvalidFormatException
     */
    String export(String startTime, String endTime) throws FileNotFoundException, InvalidFormatException, ParseException;

}
