package com.example.mystudydemo.redis;

import com.alibaba.fastjson.JSONArray;
import com.example.mystudydemo.entity.User;
import com.example.mystudydemo.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/6/21 13:57
 * @description:
 */
@Slf4j
@Component
public class RedisStudy {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    UserMapper mapper;

//    @PostConstruct
/*    private void insertRedis() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-mm hh:MM:ss");
        log.info("更新redis数据开始：" + sdf.format(new Date()));
        List<User> all = mapper.getAll();
        for (User u : all) {
            redisTemplate.opsForList().leftPush("redisStudy", JSONArray.toJSONString(u));
        }
        log.info("更新redis数据结束：" + sdf.format(new Date()));
    }*/
}
