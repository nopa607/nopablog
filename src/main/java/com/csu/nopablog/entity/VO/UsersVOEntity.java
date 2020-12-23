package com.csu.nopablog.entity.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.csu.nopablog.entity.UserEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 21:44 2020/12/21
 */
@Data
@TableName(value="users")
public class UsersVOEntity extends UserEntity implements Serializable {

    private static final long serialVersionUID = 5912785251256840233L;

    @TableField(exist = false)
    private Set<RoleVOEntity> roles;

}
