package com.csu.nopablog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csu.nopablog.dao.UserDao;
import com.csu.nopablog.entity.UserEntity;
import com.csu.nopablog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 14:22 2020/10/18
 */
@Service
public class UserImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public UserEntity findUserMess(String username) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        UserEntity userEntity = userDao.selectOne(queryWrapper.eq("username", username));
        return userEntity;
    }
}
