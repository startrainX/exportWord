package com.example.mystudydemo.redis;

import com.example.mystudydemo.config.RedisConfig;
import com.example.mystudydemo.config.SystemConfig;
import redis.clients.jedis.Jedis;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/6/21 14:34
 * @description: 测试发布订阅  发布端
 */
public class RedisPub {

    public static void main(String[] args) {

        System.out.println("---------------" + "redis发布消息开始");
        RedisConfig redisConfig = new RedisConfig("127.0.0.1");
        Jedis jedis = new Jedis("127.0.0.1");
        String jsonObject = "test";
        jedis.lpush("lll",jsonObject);
        redisConfig.publish(SystemConfig.getConfigFileRedis("redis.topic"), jsonObject);
        System.out.println("redis发布消息结束");
    }
}
