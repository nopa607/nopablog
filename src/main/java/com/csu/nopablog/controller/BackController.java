package com.csu.nopablog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Author: nopa
 * @Description:负责所有页面跳转
 * @Date: Create in 22:07 2020/12/22
 */
@Controller
public class BackController {

    /**
     * 首页
     * @return
     */
    @GetMapping(value = {"/", "index"})
    public String index() {
        return "index";
    }


    /**
     *
     * 登录页面
     * @param
     * @return
     */
    @GetMapping("login")
    public String login() {
        return "login";
    }

    /**
     * 注册页面
     * @return
     */
    @GetMapping("register")
    public String register() {
        return "register";
    }

    /**
     * 跳转到最近访问页面，如果没有则跳转到index
     *
     * @return
     */
    @GetMapping("lastUrl")
    public String lastUrl(HttpServletResponse response, HttpServletRequest request) {
        //设置跨域请求为所有都允许
        response.setHeader("Access-Control-Allow-Origin", "*");
        String lastUrl = (String) request.getSession().getAttribute("lastUrl");
        System.out.println("==========");
        System.out.println(lastUrl);
        System.out.println("==========");
        if (!StringUtils.isEmpty(lastUrl)) {
//            response.setHeader("lastUrl", lastUrl);
            response.setHeader("lastUrl", "/user/");
            return "lastUrl";
        } else {
            return "index";
        }
    }


}
