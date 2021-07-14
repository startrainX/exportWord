package com.example.mystudydemo.controller;


import com.example.mystudydemo.service.impl.VehPassRecServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/5/31 21:28
 * @description:
 */
@ResponseBody
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    VehPassRecServiceImpl vehPassRecService;

    @GetMapping("/thread")
    public void get(){
        Thread thread = new Thread();
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(thread,1, 5, TimeUnit.SECONDS);
    }

    @GetMapping("/service")
    public void getS(){
        vehPassRecService.get();
    }

    public static void main(String[] args) {
        // 随便虚拟一个日期
        LocalDate now = LocalDate.now();
        System.out.println("当前日期: " + now + " " + now.getDayOfWeek());
        // 求这个日期上一周的周一、周日
        LocalDate todayOfLastWeek = now.minusDays(7);
        LocalDate monday = todayOfLastWeek.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);
        LocalDate sunday = todayOfLastWeek.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).minusDays(1);
        System.out.println("当前日期：" + now + " 上一周的周一：" + monday + " " + monday.getDayOfWeek());
        System.out.println("当前日期：" + now + " 上一周的周日：" + sunday + " " + sunday.getDayOfWeek());


        LocalDate with = now.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate start = now.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println("上月末：" + with);
        System.out.println("上月初：" + start);
    }
}
