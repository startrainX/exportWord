package com.example.mystudydemo.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/6/1 9:05
 * @description: 线程中为了线程安全，防注入，无法自动注入实体，使用工具类手动注入
 */

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;

    }

    public static <T> T getBeanByClass(Class<T> c){
        return context.getBean(c);
    }

    public static Object getBeanByName(String name){
        return context.getBean(name);
    }
}
