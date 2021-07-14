package com.example.mystudydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mystudydemo.entity.VioForceInfoEntity;
import com.example.mystudydemo.entity.bean.VioForceInfoExportBean;
import com.example.mystudydemo.mapper.VioForceInfoMapper;
import com.example.mystudydemo.service.VioForceInfoService;
import com.example.mystudydemo.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/5/27 9:37
 * @description:
 */
@Service
public class VioForceInfoServiceImpl extends ServiceImpl<VioForceInfoMapper,VioForceInfoEntity> implements VioForceInfoService {

    @Autowired
    VioForceInfoMapper vioForceInfoMapper;

    @Override
    public List<VioForceInfoExportBean> searchDataExport(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<VioForceInfoEntity> all = vioForceInfoMapper.getAll(startTime, endTime);
        List<VioForceInfoExportBean> list = new ArrayList<>();
        try {
            for (VioForceInfoEntity entity : all) {
                VioForceInfoExportBean bean = new VioForceInfoExportBean();
                bean.setPzbh(entity.getPzbh());
                bean.setCjbj(entity.getCjbj());
                bean.setCode(entity.getCode());
                bean.setFxjg(entity.getFxjg());
                bean.setName(entity.getName());
                bean.setWfxw1(entity.getWfxw1());
                bean.setWfxw(entity.getWfxw());
                bean.setTime(DateUtil.getTimeDifference(sdf.parse(entity.getWfsj()),new Date()));
                bean.setWfsj(entity.getWfsj());
                list.add(bean);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
