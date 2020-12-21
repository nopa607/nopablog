package com.csu.nopablog.controller;

import com.csu.nopablog.entity.UserEntity;
import com.csu.nopablog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 14:51 2020/10/18
 */

@Controller
//@RequestMapping("/user/")
public class UserController {
    @Autowired
    UserService userService;

//    @GetMapping("/detail")
//    public UserEntity getUserDetail(String username) {
//        UserEntity userEntity = userService.findUserMess(username);
//        return userEntity;
//    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/detail")
    public String detail() {
        return "user/detail";
    }

    @GetMapping("/update")
    public String update() {
        return "user/update";
    }


}
