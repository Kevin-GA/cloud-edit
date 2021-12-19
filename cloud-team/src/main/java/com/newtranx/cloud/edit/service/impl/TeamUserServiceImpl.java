package com.newtranx.cloud.edit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newtranx.cloud.edit.dao.TeamUserMapper;
import com.newtranx.cloud.edit.dto.TeamUserDto;
import com.newtranx.cloud.edit.dto.TeamUserListDto;
import com.newtranx.cloud.edit.entities.TeamUserPlus;
import com.newtranx.cloud.edit.entities.TeamUser;
import com.newtranx.cloud.edit.service.TeamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * 团队成员service
 * @author 佟文森
 */
@Service
public class TeamUserServiceImpl extends ServiceImpl<TeamUserMapper, TeamUser> implements TeamUserService {

    @Autowired
    private TeamUserMapper teamUserMapper;

    @Override
    public List<TeamUserListDto> getTeamUserList(TeamUserDto teamUserDto) {
        return teamUserMapper.getTeamUserList(teamUserDto);
    }

    @Override
    public List<TeamUserPlus> getTeamUserByTeamId(TeamUserDto teamUserDto) {
        return teamUserMapper.getTeamUserByTeamId(teamUserDto);
    }

    @Override
    public int countTeamUserByTeamId(TeamUserDto teamUserDto) {
        return teamUserMapper.countTeamUserByTeamId(teamUserDto);
    }

}
