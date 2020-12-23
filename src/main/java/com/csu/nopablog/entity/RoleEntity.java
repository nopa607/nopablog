package com.csu.nopablog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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

    private static final long serialVersionUID = 5361561643044026089L;
    /**
     * 标识符
     */
    @TableId
    private Integer rid;
    /**
     * 角色名
     */
    private String rname;



}
