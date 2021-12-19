package com.newtranx.cloud.edit.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newtranx.cloud.edit.base.BaseController;
import com.newtranx.cloud.edit.base.BaseUser;
import com.newtranx.cloud.edit.common.entities.Result;
import com.newtranx.cloud.edit.common.enums.FailureCodeEnum;
import com.newtranx.cloud.edit.config.DocApiConfig;
import com.newtranx.cloud.edit.dto.*;
import com.newtranx.cloud.edit.entities.Project;
import com.newtranx.cloud.edit.entities.ProjectFile;
import com.newtranx.cloud.edit.entities.Task;
import com.newtranx.cloud.edit.entities.User;
import com.newtranx.cloud.edit.service.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;


import java.util.*;


/**
 * @Author: niujiaxin
 * @Date: 2021-02-01 23:49
 */
@RestController
@Slf4j
public class ProjectController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);


    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectFileService projectFileService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DocApiConfig docApiConfig;

//    @Autowired
//    private MongoTemplate mongoTemplate;

    //新增项目
    @PostMapping("create")
    public Result<Object> create(Project project){
        try {
            String teamId = userService.getTeamIdByUserId(getUserId().toString());
            project.setCreateBy(getUserId().toString());
            project.setTeamId(teamId);
            project.setUpdateTime(new Date());
            project.setCreateTime(new Date());
            int res = projectService.create(project);
            if(res>0)
                return Result.getSuccessResult(project);
            else
                return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
    }

    //删除项目
    @GetMapping("del")
    public Result<Object> del(Long projectId){
        try {
            projectService.del(projectId);
            taskService.delTaskByProjectId(projectId);
            return Result.getSuccessResult();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR);

    }

    //项目分页查询
    @PostMapping("getPage")
    public Result<IPage> page(@RequestBody ProjectParamVo projectParamVo){
        BaseUser baseUser = getBaseUser();
        boolean admin = baseUser.isAdmin();
        //根据权限封装userIds
        List<String> userStrs = new ArrayList<>();
        if(!admin){
            userStrs.add(getUserId().toString());
        }else{
            List<TeamUser> teamUsers = userService.selectUserIdsByUserId(Long.decode(getUserId().toString()));
            for(TeamUser teamUser:teamUsers){
                userStrs.add(teamUser.getUserId());
            }
        }
        //分页接口查询
        try {
            projectParamVo.setUserId(getUserId().toString());
            Page<Project> page = new Page<Project>(projectParamVo.getPageNum(),projectParamVo.getPageSize());
            IPage<Project> pageModel = projectService.getPage(projectParamVo,page,userStrs);
            return Result.getSuccessResult(pageModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR);
    }



    //获取项目详情
    @GetMapping("detail")
    public Result<ProjectVo> detail(Long projectId){
        ProjectVo projectVo = new ProjectVo();
        //项目详情
        projectVo.setProject(projectService.getProjectById(projectId));
        //文件列表
        projectVo.setProjectFiles(projectFileService.getFilesByProjectId(projectId));
        return Result.getSuccessResult(projectVo);

    }

    //修改项目
    @PostMapping("update")
    public Result<Project> update(Project project){
        try {
            projectService.update(project);
            return Result.getSuccessResult();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR);
    }

    //获取项目相关人员
    @GetMapping("getUsers")
    public Result<Object> uploadfiles(Long projectId){
        List<UserVo> userVos = new ArrayList<>();
        //通过项目id查询可分配人员
        List<User> usersByProjectId = userService.getUsersByProjectId(projectId);
        for (User user:usersByProjectId) {
            UserVo userVo = new UserVo();
            userVo.setUserId(user.getUserId());
            userVo.setUsername(user.getUsername());
            userVos.add(userVo);
        }

        return Result.getSuccessResult(userVos);

    }


    //分配时候查询没有百分百分配的文档
    @GetMapping("getUnAssignFiles")
    public Result<Object> getUnAssignFiles(Long projectId) {
        List<ProjectFile> list = new ArrayList<>();
        try{
            list = projectFileService.getUnAssignFilesByProjectId(projectId);
            return Result.getSuccessResult(list);

        }catch (Exception e){
            return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR);

        }

    }

    //只获取编辑和校对的文件
    @GetMapping("getEditAndCheckFiles")
    public Result<Object> getEditAndCheckFilesByProjectId(Long projectId) {
        List<ProjectFile> list = new ArrayList<>();
        try{
            list = projectFileService.getEditAndCheckFilesByProjectId(projectId);
            return Result.getSuccessResult(list);

        }catch (Exception e){
            return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR);

        }

    }

    //获取待分配的工作流程
    @GetMapping("getProcess")
    public Result<Object> getProcess(@RequestParam("fileIds") List<Long> fileIds,@RequestParam("projectId")Long projectId){
        try{
            return Result.getSuccessResult(taskService.getReduceProcess(fileIds, projectId));

        }catch (Exception e){
            return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR);

        }
    }

    //分配
    @PostMapping("assgin")
    public Result<Object> assgin(Task task){
        task.setUserId((Long) getUserId());
        int res = taskService.assign(task);
        if(res>0)
            return Result.getSuccessResult(res);
        else
            return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR);
    }



}
