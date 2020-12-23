package com.csu.nopablog.controller;

import com.csu.nopablog.common.ustils.BlogResponser;
import com.csu.nopablog.common.ustils.Constant;
import com.csu.nopablog.common.ustils.RedisOperator;
import com.csu.nopablog.dao.UserDao;
import com.csu.nopablog.entity.UserEntity;
import com.csu.nopablog.service.RegisterService;
import com.csu.nopablog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 9:57 2020/10/19
 */
@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private RedisOperator redisOperator;


    /**
     * 注册新用户
     * 只负责注册，验证交给其他方法
     *
     * @param userEntity
     * @return
     */
    @PostMapping("/register")
    public BlogResponser register(@RequestBody UserEntity userEntity) {
        int res = registerService.insUsers(userEntity);
        // 缓存中删除用户的手机信息，保护隐私
        if (res > 0) {
            System.out.println("===============");
            System.out.println(Constant.USER_PHONE_CODE + userEntity.getPhone());
            System.out.println("===============");
            redisOperator.del(Constant.USER_PHONE_CODE + userEntity.getPhone());
            return BlogResponser.ok();
        }
        return BlogResponser.errorMsg("注册失败");
    }

    /**
     * 检测手机号码的注册状态
     *
     * @param phone
     * @return
     */
    @GetMapping("/phoneCheck")
    public BlogResponser phoneCheck(@RequestParam("phone") String phone) {
        //缓存查询
        if (redisOperator.hasKey(Constant.USER_PHONE_EXIST)) {
            if (!redisOperator.hasHkey(Constant.USER_PHONE_EXIST, phone)) {
                //缓存穿透查库
                int res = registerService.findByPhone(phone);
                if (res == 0) {
                    return BlogResponser.ok();
                } else {
                    return BlogResponser.errorMsg("该手机号码已被注册!");
                }
            } else
                return BlogResponser.errorMsg("该手机号码已被注册!");
        } else {
            int res = registerService.findByPhone(phone);
            if (res == 0) {
                return BlogResponser.ok();
            } else {
                return BlogResponser.errorMsg("该手机号码已被注册!");
            }
        }
    }

    @GetMapping("usernameCheck")
    public BlogResponser usernameCheck(String username) {
        //缓存查询
        if (redisOperator.hasKey(Constant.USER_NAME_EXIST)) {
            if (!redisOperator.hasHkey(Constant.USER_NAME_EXIST, username)) {
                int res = registerService.findByUsername(username);
                if (res == 0) {
                    return BlogResponser.ok();
                } else {
                    return BlogResponser.errorMsg("该用户名已被注册!");
                }
            } else
                return BlogResponser.errorMsg("该用户名已被注册!");
        } else {
            int res = registerService.findByUsername(username);
            if (res == 0) {
                return BlogResponser.ok();
            } else {
                return BlogResponser.errorMsg("该用户名已被注册!");
            }
        }
    }


}

