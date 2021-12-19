package com.newtranx.cloud.edit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newtranx.cloud.edit.base.BaseController;
import com.newtranx.cloud.edit.base.BaseUser;
import com.newtranx.cloud.edit.common.entities.Result;
import com.newtranx.cloud.edit.common.enums.FailureCodeEnum;
import com.newtranx.cloud.edit.dto.UserRoleDto;
import com.newtranx.cloud.edit.entities.*;
import com.newtranx.cloud.edit.service.ProjectService;
import com.newtranx.cloud.edit.service.TeamService;
import com.newtranx.cloud.edit.service.TeamUserService;
import com.newtranx.cloud.edit.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/***
 * 项目相关接口
 * @author 佟文森
 */
@Api(value = "任务接口")
@RestController
@Slf4j
public class ProjectController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    TeamService teamService;

    @Autowired
    ProjectService projectService;

    @Autowired
    TeamUserService teamUserService;

    @ApiOperation(value = "getTeamProjectList", notes = "我的团队模块-查询当前团队的所有项目")
    @ApiResponses({@ApiResponse(code = 200, message = "成员任务列表", response = Project.class)})
    @PostMapping(value = "/getTeamProjectList")
    public Result getTeamProjectList(@RequestBody UserRoleDto userRoleDto) {
        userRoleDto.setUserId(new BigInteger(getUserId().toString()));
        BaseUser baseUser = getBaseUser();
        boolean admin = baseUser.isAdmin();
        LOG.info("用户角色_:" + admin);
        Page page = new Page(Optional.ofNullable(userRoleDto.getPageIndex()).orElse(1),Optional.ofNullable(userRoleDto.getPageSize()).orElse(10));
        if(admin){
            //用户角色管理员
            QueryWrapper<Project> projectQueryWrapper = new QueryWrapper();
            //查询该团队的所有项目
            projectQueryWrapper.lambda().eq(Project::getTeamId,userRoleDto.getTeamId());
            //查询未被删除的项目
            projectQueryWrapper.lambda().eq(Project::getIsDel,0);
            if(userRoleDto.getPageIndex() == null){
                //查询所有的 分配任务时使用
                return Result.getSuccessResult(projectService.list(projectQueryWrapper));
            }else{
                return Result.getSuccessResult(projectService.page(page,projectQueryWrapper));
            }
        }else{
            //用户角色不是管理员
            QueryWrapper<Project> projectQueryWrapper = new QueryWrapper();
            projectQueryWrapper.lambda().eq(Project::getCreateBy,userRoleDto.getUserId());
            projectQueryWrapper.lambda().eq(Project::getIsDel,0);
            if(userRoleDto.getPageIndex() == null){
                //查询所有的 分配任务时使用
                return Result.getSuccessResult(projectService.list(projectQueryWrapper));
            }else{
                return Result.getSuccessResult(projectService.page(page,projectQueryWrapper));
            }
        }

    }

}

