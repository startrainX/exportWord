package com.example.mystudydemo.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.mystudydemo.entity.User;
import com.example.mystudydemo.entity.VehPassRec;
import com.example.mystudydemo.entity.bean.UserExportBean;
import com.example.mystudydemo.entity.qo.UserQO;
import com.example.mystudydemo.mapper.UserMapper;
import com.example.mystudydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zsp
 * @version 1.0
 * @date 2021/4/12 14:20
 * @description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public User selectById(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    public void insert(User user) {
        userMapper.add(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public int delete(Long id) {
        return userMapper.delete(id);
    }

    @Override
    public List<UserExportBean> searchExcelData() {
        List<UserExportBean> list = new ArrayList<>();

        List<User> all = userMapper.getAll();
        for (User user : all) {
            UserExportBean bean = new UserExportBean();
            bean.setId(user.getId());
            bean.setUsername(user.getUserName());
            bean.setCreateTime(user.getCreatTime());
            bean.setPassword(user.getPassWord());
            bean.setRealName(user.getRealName());
            bean.setUpdateTime(user.getUpdateTime());
            list.add(bean);
        }
        return list;
    }

    @Override
    public IPage<User> getDataPage(UserQO qo) {
        Page page = new Page();
        page.setCurrent(qo.getCurrent());
        page.setSize(qo.getSize());
        IPage<User> dataPage = userMapper.getDataPage(page);
        return dataPage;
    }

    @Override
    public IPage<VehPassRec> testForEach(UserQO qo) {
        Page page = new Page();
        page.setCurrent(qo.getCurrent());
        page.setSize(qo.getSize());
        String[] a = new String[]{"01","02"};
        qo.setFxbh(Arrays.asList(a));
        List<VehPassRec> list = userMapper.testForEach(page,qo);
        page.setRecords(list);
        return page;
    }

}
