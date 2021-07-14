package com.example.mystudydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mystudydemo.entity.User;
import com.example.mystudydemo.entity.VehPassRec;
import com.example.mystudydemo.entity.qo.UserQO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/4/6 17:04
 * @description:
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过id查询用户
     * @param id
     * @return
     */
    User selectById(int id);

    /**
     * 查询所有用户
     * @return
     */
    List<User> getAll();

    /**
     * 新增用户
     * @param user
     */
    void add(User user);

    /**
     * 更新用户
     * @param user
     */
    void update(User user);

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    int delete(@Param("id") Long id);

    /**
     * 分页查询全部用户
     * @param page
     * @return
     */
    IPage<User> getDataPage(Page page);

    List<VehPassRec> testForEach(Page page, @Param("qo")UserQO userQO);
}
