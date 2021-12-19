package com.newtranx.cloud.edit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newtranx.cloud.edit.dao.TeamMapper;
import com.newtranx.cloud.edit.entities.Team;
import com.newtranx.cloud.edit.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * 团队service
 * @author 佟文森
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Autowired
    private TeamMapper teamMapper;

}
