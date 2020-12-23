package com.csu.nopablog.service.impl;

import com.csu.nopablog.common.ustils.Constant;
import com.csu.nopablog.common.ustils.RedisOperator;
import com.csu.nopablog.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 15:24 2020/12/23
 */
@Service
public class RedisImpl implements RedisService {

    @Autowired
    private RedisOperator redisOperator;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void savePhoneAndUsername(String phone, String username) {
        redisOperator.hset(Constant.USER_PHONE_EXIST, phone, 1);
        redisOperator.hset(Constant.USER_NAME_EXIST, username, 1);
    }
}
