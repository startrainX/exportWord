package com.example.mystudydemo.mapper;

import com.example.mystudydemo.entity.bean.TrafficBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/6/7 15:12
 * @description:
 */
@Mapper
@Repository
public interface TrafficMapper {
    /**
     * 查询早晚高峰拥堵道路top20
     * @param startTime
     * @param endTime
     * @param type
     * @return
     */
    List<TrafficBean> selectRoadTop20(@Param(value = "startTime")String startTime,
                                      @Param(value = "endTime")String endTime,
                                      @Param(value = "type")String type);

    /**
     * 查询三区早晚高峰拥堵道路区域占比
     * @param startTime
     * @param endTime
     * @param type
     * @return
     */
    List<TrafficBean> selectRoadPercent(@Param(value = "startTime")String startTime,
                                        @Param(value = "endTime")String endTime,
                                        @Param(value = "type")String type);

    /**
     * 查询全市日均指数和平均速度
     * @param startTime
     * @param endTime
     * @return
     */
    List<TrafficBean> selectAllTpiEveryDay(@Param(value = "startTime")String startTime,
                                        @Param(value = "endTime")String endTime);

    /**
     * 查询三区日均指数
     * @param startTime
     * @param endTime
     * @return
     */
    List<TrafficBean> selectRegionTpiEveryDay(@Param(value = "startTime")String startTime,
                                           @Param(value = "endTime")String endTime);

    /**
     * 查询三区早晚高峰指数对比
     * @param startTime
     * @param endTime
     * @param type
     * @return
     */
    List<TrafficBean> selectRegionTpiCompare(@Param(value = "startTime")String startTime,
                                        @Param(value = "endTime")String endTime,
                                             @Param(value = "preStartTime")String preStartTime,
                                             @Param(value = "preEndTime")String preEndTime,
                                        @Param(value = "type")String type);
}
