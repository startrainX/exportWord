package com.example.mystudydemo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/5/31 10:05
 * @description:
 */
public class ThreadCache {
    public static void main(String[] args) {
        // 创建一个可缓存线程池
        ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cacheThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    // 打印正在执行的缓存线程信息
                    System.out.println(Thread.currentThread().getName()
                            + "正在被执行");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
