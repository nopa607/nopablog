package com.csu.nopablog.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csu.nopablog.dao.UserDao;
import com.csu.nopablog.entity.PermissionEntity;
import com.csu.nopablog.entity.UserEntity;
import com.csu.nopablog.entity.VO.RoleVOEntity;
import com.csu.nopablog.entity.VO.UsersVOEntity;
import com.csu.nopablog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 17:06 2020/12/20
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    /**
     * 鉴权
     * 通过subject获取用户，接着用户的所有角色，接着获取到用户的所有权限
     * 把所有权限和角色提交给AuthorizationInfo
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UsersVOEntity users = (UsersVOEntity) SecurityUtils.getSubject().getPrincipal();
        List<String> permissionList = new ArrayList<>();
        List<String> roleNameList = new ArrayList<>();
        Set<RoleVOEntity> roles = users.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            for (RoleVOEntity role : roles) {
                roleNameList.add(role.getRname());
                Set<PermissionEntity> permissionSet = role.getPermissionSet();
                if (!CollectionUtils.isEmpty(permissionSet)) {
                    for (PermissionEntity permission : permissionSet) {
                        permissionList.add(permission.getPname());
                    }
                }
            }
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(permissionList);
        simpleAuthorizationInfo.addRoles(roleNameList);
        return simpleAuthorizationInfo;
    }


    /**
     * 认证,根据token内携带的用户手机号码作为认证
     * 认证成功则返回认证信息，认证要加盐值作为加密
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //token携带了用户信息
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //获取前端输入的手机号
        String phone = usernamePasswordToken.getUsername();
        UsersVOEntity vo = null;

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        UserEntity userEntity =  userDao.selectOne(queryWrapper.eq("phone", phone));
        try {
            System.out.println("invo1");
            System.out.println(phone);
//            vo = userService.findUsersByPhone(phone);
            System.out.println("invo2");
            //当前realm对象的name
            String realmName = getName();
            System.out.println("realmName:" + realmName);
            //盐值
            ByteSource credentialsSalt = ByteSource.Util.bytes(phone);
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(phone, usernamePasswordToken.getPassword(), credentialsSalt, realmName);
            return info;
        } catch (NullPointerException e) {
            throw new UnknownAccountException("该手机号不存在！");
        }
    }
}
