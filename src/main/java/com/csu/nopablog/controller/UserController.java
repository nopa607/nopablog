package com.csu.nopablog.controller;

import com.csu.nopablog.common.ustils.BlogResponser;
import com.csu.nopablog.entity.UserEntity;
import com.csu.nopablog.entity.VO.UsersVOEntity;
import com.csu.nopablog.service.UserService;
import net.sf.saxon.trans.Mode;
import org.apache.lucene.index.DocIDMerger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 14:51 2020/10/18
 */

@RestController
public class UserController {

    @Autowired
    UserService userService;


    /**
     * 登录是否过期
     * @param username
     * @param password
     * @param model
     * @return
     */
    @GetMapping("/isLogin")
    public String toLogin(String username, String password, Model model) {
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

    /**
     * 用户登录认证，由shiro完成
     * @param phone
     * @param password
     * @param session
     * @return
     */
    @GetMapping("toLogin")
    public BlogResponser toLogin(@RequestParam("phone") String phone,
                                 @RequestParam("password") String password,
                                 Session session) {
        UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            UsersVOEntity user = (UsersVOEntity) subject.getPrincipal();
            user.setPassword("");
            session.setAttribute("user", user);
            return BlogResponser.ok();
        } catch (AuthenticationException e) {
            return BlogResponser.errorMsg("输入的用户名或密码错误");
        }
    }





}
