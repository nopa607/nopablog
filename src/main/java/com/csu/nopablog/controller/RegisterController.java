package com.csu.nopablog.controller;

import com.csu.nopablog.common.ustils.BlogResponser;
import com.csu.nopablog.dao.UserDao;
import com.csu.nopablog.entity.UserEntity;
import com.csu.nopablog.service.RegisterService;
import com.csu.nopablog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 9:57 2020/10/19
 */
@RestController
@RequestMapping("register/")
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @PostMapping("")
    public BlogResponser register(@RequestBody UserEntity userEntity) {
        int res = registerService.insUsers(userEntity);
        //todo
        // redis缓存设置
        if (res > 0) {
            return BlogResponser.ok();
        }
        return BlogResponser.errorMsg("注册失败");
    }
}
