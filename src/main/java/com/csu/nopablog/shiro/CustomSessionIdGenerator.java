package com.csu.nopablog.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 11:28 2020/12/21
 */
public class CustomSessionIdGenerator implements SessionIdGenerator {

    @Override
    public Serializable generateId(Session session) {
        return "nopaBlog" + UUID.randomUUID().toString().replace("-", "");
    }
}
