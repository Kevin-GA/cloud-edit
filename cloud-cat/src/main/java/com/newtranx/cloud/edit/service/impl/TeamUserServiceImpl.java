package com.newtranx.cloud.edit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newtranx.cloud.edit.dao.TeamUserMapper;
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
}
