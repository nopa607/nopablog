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
     * 注册页面中检测手机号码的合法性
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


    /**
     * 用户注册页面中检测用户名合法性
     *
     * @param username
     * @return
     */
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


    /**
     * 注册页面获取验证码
     *
     * @param phone
     * @return
     */
    @GetMapping("getCode")
    public BlogResponser getCode(String phone) {
        //todo 验证码模块
//        String s = smsService.sendMesModel(phone, 0);
        String s = "OKOK";
        System.out.println("==============");
        System.out.println(s);
        System.out.println("==============");
        if (s.equals("OKOK")) {
            redisOperator.set(Constant.USER_PHONE_CODE, "OKOK");
//            redisOperator.set(Constant.USER_PHONE_CODE, s.toString());
            return BlogResponser.ok();
        } else {
            return BlogResponser.errorMsg("获取验证码失败");
        }
    }


    /**
     * 校验验证码的准确性
     * @param phone
     * @return
     */
    @GetMapping("getCodeReflush")
    public BlogResponser getCodeReflush(String phone) {
        if (redisOperator.hasKey(Constant.USER_PHONE_CODE)) {
            return BlogResponser.ok(redisOperator.get(Constant.USER_PHONE_CODE));
        } else {
            return BlogResponser.errorMsg("验证码错误!");
        }
    }


}

