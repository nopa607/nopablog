package com.csu.nopablog.entity.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csu.nopablog.entity.PermissionEntity;
import com.csu.nopablog.entity.RoleEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 21:48 2020/12/21
 */

@Data
@TableName("roles")
public class RoleVOEntity extends RoleEntity implements Serializable {

    private static final long serialVersionUID = 2580746427363178805L;

    /**
     * 当前角色所具备的权限
     */
    @TableField(exist = false)
    private Set<PermissionEntity> permissionSet;
}
