package com.newtranx.cloud.edit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.newtranx.cloud.edit.base.BaseController;
import com.newtranx.cloud.edit.common.entities.Result;
import com.newtranx.cloud.edit.common.enums.FailureCodeEnum;
import com.newtranx.cloud.edit.dto.PageDto;
import com.newtranx.cloud.edit.dto.TeamDto;
import com.newtranx.cloud.edit.dto.TeamUserDto;
import com.newtranx.cloud.edit.entities.TeamUserPlus;
import com.newtranx.cloud.edit.entities.Team;
import com.newtranx.cloud.edit.entities.TeamUser;
import com.newtranx.cloud.edit.service.TeamService;
import com.newtranx.cloud.edit.service.TeamUserService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/***
 * 团队相关接口
 * @author 佟文森
 */
@Api(value = "团队接口")
@RestController
@Slf4j
public class TeamController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    TeamUserService teamUserService;

    @Autowired
    TeamService teamService;

    @ApiOperation(value = "updateTeamName", notes = "添加成员模块-修改团队名称")
    @ApiResponses({@ApiResponse(code = 200, message = "Result", response = Result.class)})
    @PostMapping(value = "/updateTeamName")
    public Result updateTeamName(@RequestBody TeamDto teamDto) {
        if (teamDto.getId() == null) {
            //缺少参数 团队ID 返回参数不正确
            LOG.info("缺少参数 团队ID 返回参数不正确");
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (!StringUtils.isNoneBlank(teamDto.getTeamName())) {
            //缺少参数 团队ID 返回参数不正确
            LOG.info("缺少参数 团队名称 返回参数不正确");
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        Team team = new Team();
        BeanUtils.copyProperties(teamDto,team);
        teamService.updateById(team);
        //服务异常
        return Result.getSuccessResult();
    }

    @ApiOperation(value = "addTeamUser", notes = "添加成员模块-添加成员到团队")
    @ApiResponses({@ApiResponse(code = 200, message = "Result", response = Result.class)})
    @PostMapping(value = "/addTeamUser")
    public Result addTeamUser(@RequestBody TeamUserDto teamUserDto) {
        String adminUserId = getUserId().toString();
        //查询要添加的用户 不用token
        if (!StringUtils.isNoneBlank(teamUserDto.getUserId())) {
            //缺少参数 用户ID 返回参数不正确
            LOG.info("缺少参数 用户ID 返回参数不正确");
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (!StringUtils.isNoneBlank(teamUserDto.getRoleId())) {
            //缺少参数 角色编号 返回参数不正确
            LOG.info("缺少参数 角色编号 返回参数不正确");
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (teamUserDto.getJobType() == null) {
            //缺少参数 工作性质 返回参数不正确
            LOG.info("缺少参数 工作性质 返回参数不正确");
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
        //验证是否已经添加过
        QueryWrapper<TeamUser> queryWrapper = new QueryWrapper();
        //统计该团队下 该用户是否已添加
        queryWrapper.lambda().eq(TeamUser::getUserId,teamUserDto.getUserId());
        queryWrapper.lambda().eq(TeamUser::getTeamId,teamUserDto.getTeamId());
        queryWrapper.lambda().eq(TeamUser::getIsDel,0);
        int userCount = teamUserService.count(queryWrapper);
        if(userCount > 0){
            //TODO 需要定义一些业务code
            return Result.getFailureResult("","该人员已添加");
        }
        String[] roleArray = teamUserDto.getRoleId().split(",");
        QueryWrapper<TeamUser> queryTeamUserWrapper = new QueryWrapper();
        queryTeamUserWrapper.lambda().eq(TeamUser::getTeamId, teamUserDto.getTeamId());
        queryTeamUserWrapper.lambda().eq(TeamUser::getUserId, teamUserDto.getUserId());
        queryTeamUserWrapper.lambda().eq(TeamUser::getIsDel,"0");
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
            teamUser.setTeamId(teamUserDto.getTeamId());
            teamUser.setJobType(teamUserDto.getJobType());
            teamUser.setRoleId(roleArray[i]);
            teamUser.setCreateTime(new Date());
            teamUser.setUpdateTime(new Date());
            teamUserService.save(teamUser);
        }
        return Result.getSuccessResult();
    }

    @ApiOperation(value = "getUserTeamMemberList", notes = "我的团队模块-团队成员列表")
    @ApiResponses({@ApiResponse(code = 200, message = "团队成员列表", response = TeamUser.class)})
    @PostMapping(value = "/getUserTeamMemberList")
    public Result getUserTeamMemberList(@RequestBody TeamUserDto teamUserDto) {
        teamUserDto.setUserId(getUserId().toString());
        if (!StringUtils.isNoneBlank(teamUserDto.getUserId())) {
            //没有当前登录用户ID
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (teamUserDto.getPageIndex() == null) {
            //缺少参数 页数 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        //TODO 先假设由前端传入，后面具体对接时确认方案，加入默认值
        if (teamUserDto.getPageSize() == null) {
            //TODO 产品定义 每页20条 后面具体对接时确认是否可以修改page封装将20条作为默认
            teamUserDto.setPageSize(20);
        }
        log.info("[getUserTeamMemberList] 团队成员列表 userId _:" + teamUserDto.getUserId());
        QueryWrapper<TeamUser> queryAdminUserWrapper = new QueryWrapper();
        queryAdminUserWrapper.lambda().eq(TeamUser::getUserId,teamUserDto.getUserId());
        List<TeamUser> adminUserTeam = teamUserService.list(queryAdminUserWrapper);
        if(adminUserTeam.size() < 1){
            return Result.getFailureResult("","该管理员没有团队");
        }
        teamUserDto.setTeamId(adminUserTeam.get(0).getTeamId());
        log.info("[getUserTeamMemberList] 团队成员列表 teamId _:" + teamUserDto.getTeamId());
        PageDto PageDto = new PageDto();
        PageDto.setTeamUserList(teamUserService.getTeamUserByTeamId(teamUserDto));
        PageDto.setTotal(teamUserService.countTeamUserByTeamId(teamUserDto));
        return Result.getSuccessResult(PageDto);
    }

    @ApiOperation(value = "getTeamUserList", notes = "我的团队模块-团队成员列表")
    @ApiResponses({@ApiResponse(code = 200, message = "团队成员列表", response = TeamUser.class)})
    @PostMapping(value = "/getTeamUserList")
    public Result getTeamUserList(@RequestBody TeamUserDto teamUserDto) {
        //TODO 先假设当前端能提供当前管理员的团队ID
        if (!StringUtils.isNoneBlank(teamUserDto.getTeamId())) {
            //缺少参数 团队ID 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (teamUserDto.getPageIndex() == null) {
            //缺少参数 页数 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        //TODO 先假设由前端传入，后面具体对接时确认方案，加入默认值
        if (teamUserDto.getPageSize() == null) {
            //TODO 产品定义 每页20条 后面具体对接时确认是否可以修改page封装将20条作为默认
            teamUserDto.setPageSize(20);
        }
        //查询总数
        QueryWrapper<TeamUser> queryWrapper = new QueryWrapper();
        //是否使用了工作性质做筛选
        if(teamUserDto.getJobType() != null){
            //工作性质不为空 作为查询条件加入
            queryWrapper.lambda().eq(TeamUser::getJobType, teamUserDto.getJobType());
        }
        if(teamUserDto.getTeamId() != null){
            queryWrapper.lambda().eq(TeamUser::getTeamId, teamUserDto.getTeamId());
        }
        //添加数据状态 只能看到未被删除的成员
        queryWrapper.lambda().eq(TeamUser::getIsDel,0);
        PageDto PageDto = new PageDto();
        PageDto.setTeamUserList(teamUserService.getTeamUserList(teamUserDto));
        PageDto.setTotal(teamUserService.count(queryWrapper));
        //返回给前端
        return Result.getSuccessResult(PageDto);
    }

    @ApiOperation(value = "getTeamUserCount", notes = "我的团队模块-当前团队总人数")
    @ApiResponses({@ApiResponse(code = 200, message = "Result", response = Result.class)})
    @PostMapping(value = "/getTeamUserCount")
    public Result getTeamUserCount(@RequestBody TeamUserDto teamUserDto) {
        //TODO 先假设当前端能提供当前管理员的团队ID
        if (!StringUtils.isNoneBlank(teamUserDto.getTeamId())) {
            //缺少参数 团队ID 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        //创建查询构造器 用来组装查询条件
        QueryWrapper<TeamUser> queryWrapper = new QueryWrapper();
        //组装teamId等于前端传来的teamId
        queryWrapper.lambda().eq(TeamUser::getTeamId, teamUserDto.getTeamId());
        //调用通用service封装的查询统计
        int teamUserCount = teamUserService.count(queryWrapper);
        //返回统计结果
        return Result.getSuccessResult(teamUserCount);
    }

    @ApiOperation(value = "delTeamUser", notes = "我的团队模块-删除团队成员")
    @ApiResponses({@ApiResponse(code = 200, message = "Result", response = Result.class)})
    @PostMapping(value = "/delTeamUser")
    public Result delTeamUser(@RequestBody TeamUserDto teamUserDto) {
        //要删除的用户ID 不用token
        if (!StringUtils.isNoneBlank(teamUserDto.getUserId())) {
            //缺少参数 用户ID 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        //TODO 先假设当前端能提供当前管理员的团队ID
        if (!StringUtils.isNoneBlank(teamUserDto.getTeamId())) {
            //缺少参数 团队ID 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        QueryWrapper<TeamUser> queryWrapper = new QueryWrapper();
        //组装teamId等于前端传来的teamId
        queryWrapper.lambda().eq(TeamUser::getTeamId, teamUserDto.getTeamId());
        //调用通用service封装的查询统计
        int teamUserCount = teamUserService.count(queryWrapper);
        if(teamUserCount < 1){
            return Result.getFailureResult("PARAM_NULL","团队不存在或团队内无成员");
        }
        //创建修改构造器 用来组装修改使用的查询条件
        UpdateWrapper<TeamUser> updateWrapper = new UpdateWrapper();
        //组装teamId等于前端传来的teamId
        updateWrapper.lambda().eq(TeamUser::getTeamId, teamUserDto.getTeamId());
        //组装userId等于前端传来的userId
        updateWrapper.lambda().eq(TeamUser::getUserId, teamUserDto.getUserId());
        //修改时间
        updateWrapper.lambda().set(TeamUser::getUpdateTime,new Date());
        //加入数据状态 已删除
        updateWrapper.lambda().set(TeamUser::getIsDel,1);
        //调用通用service封装的修改 按构造器修改
        boolean updateState = teamUserService.update(updateWrapper);
        return Result.getSuccessResult();
    }
}

