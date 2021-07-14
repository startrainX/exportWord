package com.example.mystudydemo.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mystudydemo.entity.VehPassRec;
import com.example.mystudydemo.mapper.VehPassRecMapper;
import com.example.mystudydemo.service.VehPassRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/5/31 20:14
 * @description:
 */
@Service
public class VehPassRecServiceImpl extends ServiceImpl<VehPassRecMapper, VehPassRec> implements VehPassRecService {
    @Autowired
    VehPassRecMapper vehPassRecDao;

    public void get() {
        vehPassRecDao.getVehPass("3310002010027474529389");
    }
}
