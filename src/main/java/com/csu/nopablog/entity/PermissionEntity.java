package com.csu.nopablog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 21:55 2020/12/21
 */

@Data
@TableName("permission")
public class PermissionEntity implements Serializable {

    private static final long serialVersionUID = 8637009524612094487L;
    @TableId
    private Integer pid;
    private String pname;
    private String url;
}
