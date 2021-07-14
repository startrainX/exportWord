package com.example.mystudydemo.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/5/31 10:12
 * @description:
 */
public class ThreadScheduler {
    public static void main(String[] args) {
        //创建一个定长线程池，支持定时及周期性任务执行——延迟执行
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

        //延迟1秒后每3秒执行一次
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("延迟1秒后每3秒执行一次");
                System.out.println(Thread.currentThread().getName()
                        + "正在被执行");
            }
        }, 1, 3, TimeUnit.SECONDS);

    }
}
