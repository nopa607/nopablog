package com.csu.nopablog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * @Author: nopa
 * @Description:负责所有页面跳转
 * @Date: Create in 22:07 2020/12/22
 */
@Controller
public class BackController {

    /**
     * 登录页面
     * @param
     * @return
     */
    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

}
