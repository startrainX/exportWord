package com.example.mystudydemo;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * start
 * @author startRain
 */
@EnableSwagger2
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.example.mystudydemo.mapper")
public class MyStudyDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyStudyDemoApplication.class, args);
    }

}
