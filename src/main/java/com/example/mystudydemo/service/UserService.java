package com.example.mystudydemo.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mystudydemo.entity.User;
import com.example.mystudydemo.entity.VehPassRec;
import com.example.mystudydemo.entity.bean.UserExportBean;
import com.example.mystudydemo.entity.qo.UserQO;

import java.util.List;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/4/6 17:04
 * @description:
 */
public interface UserService extends IService<User> {

    /**
     * 根据id查询
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
     * @return
     */
    void insert(User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    void update(User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 查询所有用户
     * @return
     */
    List<UserExportBean> searchExcelData();

    IPage<User> getDataPage(UserQO qo);

    IPage<VehPassRec> testForEach(UserQO qo);
}