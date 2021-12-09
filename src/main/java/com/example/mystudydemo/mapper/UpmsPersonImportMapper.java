package com.example.mystudydemo.mapper;


import com.example.mystudydemo.entity.po.ImportPersonPO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/12/7 11:09
 * @description:
 */
@Mapper
public interface UpmsPersonImportMapper {

    @MapKey("name")
    List<Map<String,String>> getDepts();

    Long insertPerson(@Param("po") ImportPersonPO po);

    void insertDeptPerson(@Param("po")ImportPersonPO po);

    void insertUser(@Param("po")ImportPersonPO po);
}
