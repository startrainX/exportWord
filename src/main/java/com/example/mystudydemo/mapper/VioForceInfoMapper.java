package com.example.mystudydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mystudydemo.entity.VioForceInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/5/27 9:30
 * @description:
 */
@Mapper
@Repository
public interface VioForceInfoMapper extends BaseMapper<VioForceInfoEntity> {

    List<VioForceInfoEntity> getAll(String startTime, String endTime);
}
