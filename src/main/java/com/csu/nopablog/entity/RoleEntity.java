package com.csu.nopablog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 21:49 2020/12/21
 */
@Data
@TableName("roles")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = -1638648419098790841L;
    /**
     * 标识符
     */
    private Integer rid;

    /**
     * 角色名
     */
    private String rname;



}
