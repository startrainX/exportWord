package com.example.mystudydemo.redis;


import com.example.mystudydemo.config.RedisConfig;
import com.example.mystudydemo.config.SystemConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.Date;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/3/31 13:15
 * @description:测试发布订阅  消费端
 */
public class RedisSub extends JedisPubSub {
    /**
     * 接收到消息时执行
     * @param channel
     * @param message
     */
    @Override
    public void  onMessage(String channel, String message){
        System.out.println("oneJedisPubSub message is" + message);
        System.out.println("连接redis,时间：" + new Date());
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(500);
        config.setMaxIdle(300);
        config.setMaxWaitMillis(5000L);
        config.setTestOnBorrow(false);
        JedisPool jedisPool = new JedisPool(config,"127.0.0.1",6379,1000);
        System.out.println("连接成功");
        Jedis jedis = jedisPool.getResource();
        jedis.lpush("message",message);
    }

    /**
     * 接收到模式消息时执行
     * @param pattern
     * @param channel
     * @param message
     */
    @Override
    public void onPMessage(String pattern, String channel, String message){
        System.out.println("oneJedisPubSub pattern是"+pattern+"channel是"+channel + "message是" + message);
    }

    /**
     * 订阅时执行
     * @param channel
     * @param subscribedChannels
     */
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("oneJedisPubSub订阅");
    }

    /**
     * 取消订阅时执行
     * @param channel
     * @param subscribedChannels
     */
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels){
        System.out.println("oneJedisPubSub取消订阅"+channel);
    }

    /**
     * 取消模式订阅时执行
     * @param pattern
     * @param subscribedChannels
     */
    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.println("oneJedisPubSub取消多订阅"+pattern);
    }

    @Override
    public void onPSubscribe(String s, int i) {

    }

    public static void main(String[] args) throws Exception {
        final RedisSub redisSub = new RedisSub();
        RedisConfig redisConfig = new RedisConfig("127.0.0.1");
        redisConfig.subscribe(redisSub, SystemConfig.getConfigFileRedis("redis.topic"));
    }
}
