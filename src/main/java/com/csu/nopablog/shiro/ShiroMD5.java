package com.csu.nopablog.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 16:13 2020/10/18
 */
public class ShiroMD5 {
    public static Object MD5(String username,String password){
        String hashAlgorithName = "MD5";
        int hashIterations = 1024;//加密次数
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);
        Object obj = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
        return obj;
    }

    public static void main(String[] args){
        String username = "123";
        String password = "123456";
        System.out.println(MD5(username,password));


    }
}
