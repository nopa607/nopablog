package com.csu.nopablog.service.impl;

import com.csu.nopablog.shiro.ShiroMD5;
import com.csu.nopablog.common.ustils.TimeUtil;
import com.csu.nopablog.dao.UserDao;
import com.csu.nopablog.entity.UserEntity;
import com.csu.nopablog.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 15:56 2020/10/18
 */
@Service
public class RegisterImpl implements RegisterService {

    @Autowired
    UserDao userDao;

    @Override
    public int insUsers(UserEntity user) {
        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        user.setRoleId(2);
        user.setPassword(ShiroMD5.MD5(user.getUsername(), user.getPassword()).toString());
        String date = new TimeUtil().getFormatDateForThree();
        user.setLastTime(date);
        //todo
        // 需要将手机号和用户名存入缓存redis

        return userDao.insert(user);
    }

    @Override
    public int findByPhone(String phone) {
        return 0;
    }

    @Override
    public int findByUsername(String username) {
        return 0;
    }
}
