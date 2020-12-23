package com.csu.nopablog.service;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 15:17 2020/12/23
 */

public interface RedisService {

    /**
     * 把用户的手机号码和用户名存入缓存
     * @param phone
     * @param username
     */
    public void savePhoneAndUsername(String phone, String username);


}
