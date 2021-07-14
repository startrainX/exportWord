package com.example.mystudydemo.thread;

import java.util.concurrent.*;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/5/31 9:32
 * @description:
 */
public class ThreadTest {
    static ConcurrentHashMap<Integer, String> hashMap = new ConcurrentHashMap();
    public static void main(String[] args) {
        //核心线程数
        int corePoolSize = 3;
        //最大线程数
        int maximumPoolSize = 6;
        //超过 corePoolSize 线程数量的线程最大空闲时间
        long keepAliveTime = 2;
        //以秒为时间单位
        TimeUnit unit = TimeUnit.SECONDS;
        //创建工作队列，用于存放提交的等待执行任务
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(2);
        ThreadPoolExecutor threadPoolExecutor = null;
        try {
            //创建线程池
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                    maximumPoolSize,
                    keepAliveTime,
                    unit,
                    workQueue,
                    new ThreadPoolExecutor.CallerRunsPolicy());

            //循环提交任务
            for (int i = 0; i < 9; i++) {
                //提交任务的索引
                final int index = (i + 1);
                threadPoolExecutor.submit(() -> {
                    if (hashMap.size() == 0) {
                        hashMap.put(99,"" + index);
                    } else {
                        System.out.println("hashMap中的线程" + hashMap.get(99));
                    }
                    //线程打印输出
                    System.out.println("大家好，我是线程：" + index);
                    hashMap.put(99,"" + index);
                    try {
                        //模拟线程执行时间，10s
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                //每个任务提交后休眠500ms再提交下一个任务，用于保证提交顺序
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }
    }
}
