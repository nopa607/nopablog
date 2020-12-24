package com.csu.nopablog.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 10:38 2020/12/21
 */
@Configuration
public class ShiroConfig {

    @Value("${nopaBlog.shiro-redis.host}")
    private String shiroRedisHost;

    @Value("${nopaBlog.shiro-redis.port}")
    private Integer shiroRedisPort;


    /**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    /**
     * 自定义会话管理， 采用sessionId
     * 加上redis持久化
     *
     * @return
     */
    public SessionManager sessionManager() {
        CustomSessionManager customSessionManager = new CustomSessionManager();
        // 会话超时设置为1天，默认是30min
        customSessionManager.setGlobalSessionTimeout(60 * 1000 * 60 * 24);
        customSessionManager.setSessionDAO(redisSessionDAO());
        return customSessionManager;
    }

    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(getRedisManager());
        // 设置sessionId生成器
        redisSessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());
        return redisSessionDAO;

    }

    /**
     * 配置具体cache实现类
     *
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(getRedisManager());
        // 设置过期时间，单位是秒
        redisCacheManager.setExpire(60 * 60 * 24);
        return redisCacheManager;
    }


    /**
     * 设置redisManager
     *
     * @return
     */
    public RedisManager getRedisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(shiroRedisHost);
        redisManager.setPort(shiroRedisPort);
        return redisManager;
    }

    /**
     * shiro内置过滤器，实现权限拦截
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();

//        //虽然和下面的方法是一样的，但是下面的方法会创建bean，从而可以使用依赖的dao和service，这会影响登录等等功能
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        CustomSessionManager customSessionManager = new CustomSessionManager();
//        customSessionManager.setGlobalSessionTimeout(60 * 1000 * 60 * 24);
//        customSessionManager.setSessionDAO(redisSessionDAO());
//        securityManager.setSessionManager(customSessionManager);
//        securityManager.setCacheManager(redisCacheManager());
//        AuthRealm authRealm = new AuthRealm();
//        authRealm.setCredentialsMatcher(hashedCredentialsMatcher());
//        securityManager.setRealm(authRealm);
//        bean.setSecurityManager(securityManager);

        bean.setSecurityManager(securityManager());


        //设置默认拦截无登录用户的跳转页面
        bean.setLoginUrl("login");
        //无资源权限时(未授权)跳转的页面
//        bean.setUnauthorizedUrl("/");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/", "anon");    // authc --    认证(登录)才能使用
//        filterChainDefinitionMap.put("/user", "authc");
//        filterChainDefinitionMap.put("/editor", "roles[admin]");
//        filterChainDefinitionMap.put("/SuperAdmin", "roles[admin]");
//        filterChainDefinitionMap.put("/druid/**", "anon");  // anon -- 匿名访问
//        filterChainDefinitionMap.put("/**", "anon");
        filterChainDefinitionMap.put("/update", "authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    /**
     * Shiro控制ThymeLeaf界面按钮级权限控制
     *
     * @return
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


    /**
     * 自定义realm
     *
     * @return
     */
    @Bean
    public AuthRealm authRealm() {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return authRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(redisCacheManager());
        securityManager.setRealm(authRealm());
        return securityManager;
    }


    /**
     * 配置shiro跟spring的关联
     *
     * @param
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }


    /**
     * Spring的一个bean , 由Advisor决定对哪些类的方法进行AOP代理 .
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }


}
