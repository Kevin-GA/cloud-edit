package com.newtranx.cloud.edit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newtranx.cloud.edit.dao.UserMapper;
import com.newtranx.cloud.edit.entities.User;
import com.newtranx.cloud.edit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ywj
 * @since 2021-01-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username));
    }

    @Override
    public User getByRefUserId(String refUserId) {
        return userMapper.getByRefUserId(refUserId);
    }
}
