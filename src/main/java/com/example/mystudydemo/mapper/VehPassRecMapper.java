package com.example.mystudydemo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mystudydemo.entity.VehPassRec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/5/31 19:02
 * @description:
 */
@Mapper
@Repository
public interface VehPassRecMapper extends BaseMapper<VehPassRec> {

    List<VehPassRec> getVehPass(@Param("xlh")String xlh);
}
