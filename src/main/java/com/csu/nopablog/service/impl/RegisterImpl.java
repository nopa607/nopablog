package com.csu.nopablog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csu.nopablog.service.RedisService;
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
    private UserDao userDao;

    @Autowired
    private RedisService redisService;

    @Override
    public int insUsers(UserEntity user) {

        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        user.setRoleId(2);
        user.setPassword(ShiroMD5.MD5(user.getUsername(), user.getPassword()).toString());
        String date = new TimeUtil().getFormatDateForThree();
        user.setLastTime(date);

        // 将手机号和用户名存入缓存redis
        redisService.savePhoneAndUsername(user.getPhone(), user.getUsername());

        return userDao.insert(user);
    }

    @Override
    public int findByPhone(String phone) {
        int res = userDao.selectCount(new QueryWrapper<UserEntity>().eq("phone", phone));
        //todo 异步缓存
        return res;
    }

    @Override
    public int findByUsername(String username) {
        int res = userDao.selectCount(new QueryWrapper<UserEntity>().eq("username", username));
        //todo 异步缓存
        return res;
    }
}
