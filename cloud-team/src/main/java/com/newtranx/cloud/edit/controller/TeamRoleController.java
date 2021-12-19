package com.newtranx.cloud.edit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newtranx.cloud.edit.base.BaseController;
import com.newtranx.cloud.edit.common.entities.Result;
import com.newtranx.cloud.edit.common.enums.FailureCodeEnum;
import com.newtranx.cloud.edit.dto.TeamUserDto;
import com.newtranx.cloud.edit.entities.TeamUser;
import com.newtranx.cloud.edit.service.TeamUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/***
 * 用户角色接口
 * @author 佟文森
 */
@Api(value = "用户角色接口")
@RestController
@Slf4j
public class TeamRoleController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(TeamRoleController.class);

    @Autowired
    TeamUserService teamUserService;

    @ApiOperation(value = "updateTeamUserJobType", notes = "我的团队模块-修改职能")
    @ApiResponses({@ApiResponse(code = 200, message = "Result", response = Result.class)})
    @PostMapping(value = "/updateTeamUserJobType")
    public Result updateTeamUserJobType(@RequestBody TeamUserDto teamUserDto) {
        String adminUserId = getUserId().toString();
        //修改用户职能 不是当前用户的 不用token
        if (teamUserDto.getUserId() == null) {
            //缺少参数 用户ID 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        //TODO 先假设当前端能提供 修改成哪种角色
        if (teamUserDto.getRoleId() == null) {
            //缺少参数 角色ID 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if(StringUtils.isNoneBlank(adminUserId)){
            QueryWrapper<TeamUser> queryAdminUserWrapper = new QueryWrapper();
            queryAdminUserWrapper.lambda().eq(TeamUser::getUserId,adminUserId);
            List<TeamUser> adminUserTeam = teamUserService.list(queryAdminUserWrapper);
            if(adminUserTeam.size() < 1){
                return Result.getFailureResult("","该管理员没有团队");
            }
            teamUserDto.setTeamId(adminUserTeam.get(0).getTeamId());
        }
        String[] roleArray = teamUserDto.getRoleId().split(",");
        QueryWrapper<TeamUser> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(TeamUser::getUserId, teamUserDto.getUserId());
        queryWrapper.lambda().eq(TeamUser::getIsDel,"0");
        int userRoleSize = teamUserService.count(queryWrapper);
        if(userRoleSize > 0){
            List<TeamUser> urList = teamUserService.list(queryWrapper);
            for(TeamUser ur : urList){
                ur.setIsDel("1");
                teamUserService.updateById(ur);
            }
        }
        for(int i = 0; i < roleArray.length; i++){
            TeamUser teamUser = new TeamUser();
            teamUser.setUserId(teamUserDto.getUserId());
            teamUser.setRoleId(roleArray[i]);
            teamUser.setTeamId(teamUserDto.getTeamId());
            teamUser.setJobType(0);
            teamUser.setCreateTime(new Date());
            teamUser.setUpdateTime(new Date());
            teamUserService.save(teamUser);
        }
        return Result.getSuccessResult();
    }

}

