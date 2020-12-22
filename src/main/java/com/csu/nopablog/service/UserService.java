package com.csu.nopablog.service;

import com.csu.nopablog.entity.UserEntity;
import com.csu.nopablog.entity.VO.UsersVOEntity;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 14:21 2020/10/18
 */
public interface UserService {
    UserEntity findUserMess(String username);

    UsersVOEntity findUsersByPhone(String phone);
}
