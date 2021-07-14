package com.example.mystudydemo.utils;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * describe:map缓存
 *
 * @Author: 沈思勋
 * @Date: 2019/11/11 16:33
 * @Version 1.0
 */
@Component
public class MapCache<K, V> {
    /**
     * 存放缓存容器
     */
    private ConcurrentHashMap<K, V> concurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 对传统的Map包装
     *
     * @param key   键
     * @param value 值
     */
    public void put(K key, V value) {
        concurrentHashMap.put(key, value);
    }

    /**
     * 查询
     *
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        return concurrentHashMap.get(key);
    }

    /**
     * @param k 键
     */
    public void remove(String k) {
        //这个map是安全的 不需要加锁！
        concurrentHashMap.remove(k);
    }

    public int size() {
        return concurrentHashMap.size();
    }

    public static void main(String[] args) {
        String a = "330702000000";
        int i = a.indexOf("330701");
        System.out.println(i);
    }
}
