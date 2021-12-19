package com.newtranx.cloud.edit.service;

import com.newtranx.cloud.edit.dto.TeamUser;
import com.newtranx.cloud.edit.entities.User;

import java.util.List;

/**
 * @Author: niujiaxin
 * @Date: 2021-02-05 01:09
 */
public interface UserService {

    List<User> getUsersByProjectId(Long projectId);

    String getTeamIdByUserId(String userId);

    /**
     * 查询管理员团队下的所有成员
     * @param userId
     * @return
     */
    List<TeamUser> selectUserIdsByUserId(Long userId);
}
