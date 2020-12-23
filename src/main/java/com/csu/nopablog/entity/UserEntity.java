package com.csu.nopablog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 14:00 2020/10/18
 */

@Data
@TableName("users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -4545395789994644313L;

    @TableId
    private String id;
    private String username;
    private String password;
    private String phone;
    private int sex;
    private String lastTime;
    private int roleId;
    private String realname = "";
    private String qq = "";
    private String email = "";
    private String intro = "";


}
