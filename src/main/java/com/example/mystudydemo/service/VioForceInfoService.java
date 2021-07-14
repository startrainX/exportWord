package com.example.mystudydemo.service;

import com.example.mystudydemo.entity.bean.VioForceInfoExportBean;

import java.util.List;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/5/27 9:35
 * @description:
 */
public interface VioForceInfoService {

    List<VioForceInfoExportBean> searchDataExport(String startTime, String endTime);
}
