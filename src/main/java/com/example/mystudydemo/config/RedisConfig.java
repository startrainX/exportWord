package com.example.mystudydemo.config;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.Resource;


/**
 * @author zsp
 * @version 1.0
 * @date 2021/3/31 13:06
 * @description:
 */
public class RedisConfig {
    @Resource
    private JedisPool jedisPool;

    public RedisConfig()
    {
        initialPool();
    }

    public RedisConfig(String a)
    {
        initialPool(a);
    }

    private void initialPool(String a)
    {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(500);
        config.setMaxIdle(300);
        config.setMaxWaitMillis(5000L);
        config.setTestOnBorrow(false);
        jedisPool = new JedisPool(config, a,6379,1000);
    }

    /**
     * 初始化非切片池
     */
    private void initialPool()
    {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(500);
        config.setMaxIdle(300);
        config.setMaxWaitMillis(5000L);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config, SystemConfig.getConfigFileRedis("redis.host"),6379,1000);
    }
    /**
     * 发布消息
     * @param topic
     * @param message
     */
    public Long publish(String topic,String message){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.publish(topic, message);
        } catch (Exception e) {
            throw e;
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 订阅消息
     * @param jedisPubSub
     * @param topics
     */
    public void subscribe(JedisPubSub jedisPubSub, String... topics) throws Exception {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.subscribe(jedisPubSub,topics);
        } catch (Exception e) {
            throw e;
        } finally {
            if(jedis != null){
                jedis.close();
            }

        }
    }

    /**
     * 模式匹配订阅消息
     * @param topic
     */
    public void pubsubPattern(JedisPubSub jedisPubSub,String topic){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.psubscribe(jedisPubSub,topic);
        } catch (Exception e) {
            throw e;
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }
}
