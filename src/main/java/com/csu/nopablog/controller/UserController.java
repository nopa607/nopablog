package com.csu.nopablog.controller;

import com.csu.nopablog.entity.UserEntity;
import com.csu.nopablog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 14:51 2020/10/18
 */

@Controller
public class UserController {

    @Autowired
    UserService userService;

//    @GetMapping("/detail")
//    public UserEntity getUserDetail(String username) {
//        UserEntity userEntity = userService.findUserMess(username);
//        return userEntity;
//    }


    @RequestMapping("/login")
    public String login(String username, String password, Model model) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            //login()最终交给了Realm的认证方法。
            //由于我们重写了Realm的继承类AuthRealm，最终交给它
            subject.login(token);
            return "login";
        } catch (UnknownAccountException UAE) {
            model.addAttribute("msg", "UAE");
            return "login";
        } catch (IncorrectCredentialsException ICE) {
            model.addAttribute("msg", "ICE");
            return "login";
        }
    }

    @GetMapping("/toLogin")
    public String toLogin() {
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
