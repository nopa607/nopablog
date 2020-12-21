package com.csu.nopablog.service;

import com.csu.nopablog.entity.UserEntity;
import org.springframework.stereotype.Service;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 15:54 2020/10/18
 */
public interface RegisterService {


    /**
     * 注册
     * @param users
     * @return
     */
    public int insUsers(UserEntity users);

    /**
     * 手机号检测
     * @param phone
     * @return
     */
    public int findByPhone(String phone);

    /**
     * 用户名检测
     * @param username
     * @return
     */
    public int findByUsername(String username);
}
