package com.canway.manager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.canway.manager.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<User> {
}
