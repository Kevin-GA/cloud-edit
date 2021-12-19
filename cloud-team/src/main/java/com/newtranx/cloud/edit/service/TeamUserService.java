package com.newtranx.cloud.edit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newtranx.cloud.edit.dto.TeamUserDto;
import com.newtranx.cloud.edit.dto.TeamUserListDto;
import com.newtranx.cloud.edit.entities.TeamUserPlus;
import com.newtranx.cloud.edit.entities.TeamUser;

import java.util.List;

/***
 * 团队成员service
 * @author 佟文森
 */
public interface TeamUserService extends IService<TeamUser> {

    List<TeamUserListDto> getTeamUserList(TeamUserDto teamUserDto);

    List<TeamUserPlus> getTeamUserByTeamId(TeamUserDto teamUserDto);

    int countTeamUserByTeamId(TeamUserDto teamUserDto);
}
