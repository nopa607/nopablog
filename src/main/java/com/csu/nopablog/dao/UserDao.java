package com.csu.nopablog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.nopablog.entity.UserEntity;
import org.springframework.stereotype.Repository;

/**
 * @Author: nopa
 * @Description:
 * @Date: Create in 14:15 2020/10/18
 */

@Repository
public interface UserDao extends BaseMapper<UserEntity> {

}
