package com.newtranx.cloud.edit.service.impl;

import com.newtranx.cloud.edit.dao.UserDao;
import com.newtranx.cloud.edit.dto.TeamUser;
import com.newtranx.cloud.edit.entities.User;
import com.newtranx.cloud.edit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: niujiaxin
 * @Date: 2021-02-05 01:10
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUsersByProjectId(Long projectId) {
        return userDao.getUserByProjectId(projectId);
    }

    @Override
    public String getTeamIdByUserId(String userId) {
        return userDao.getTeamIdByUserId(userId);
    }

    @Override
    public List<TeamUser> selectUserIdsByUserId(Long userId) {
        return userDao.getTeamUserInfo(userId);
    }
}
