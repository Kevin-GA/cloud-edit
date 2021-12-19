package com.newtranx.cloud.edit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newtranx.cloud.edit.dao.ProjectDao;
import com.newtranx.cloud.edit.dao.ProjectProcessDao;
import com.newtranx.cloud.edit.dao.TaskDao;
import com.newtranx.cloud.edit.dao.UserDao;
import com.newtranx.cloud.edit.dto.ProjectParamVo;

import com.newtranx.cloud.edit.entities.Project;
import com.newtranx.cloud.edit.entities.ProjectProcess;
import com.newtranx.cloud.edit.entities.Task;
import com.newtranx.cloud.edit.entities.User;
import com.newtranx.cloud.edit.enums.ProjectProcessTypesEnum;
import com.newtranx.cloud.edit.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: niujiaxin
 * @Date: 2021-02-02 00:03
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectProcessDao projectProcessDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TaskDao taskDao;

    @Transactional
    @Override
    public int create(Project project) {

        try {
            //翻译userid
            User user = userDao.selectById(project.getCreateBy());
            if (user!=null){
                if (user.getNickName() != null) {
                    project.setCreateByName(user.getNickName());
                } else {
                    project.setCreateByName(user.getUsername());
                }

            }
            projectDao.create(project);
            batchInsertProjectProcess(project);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int del(Long projectId) {
        return projectDao.isdelByProjectId(projectId);
    }

    @Override
    public Project getProjectById(Long projectId) {
        Project project= projectDao.getProjectById(projectId);
        if(project!=null){
            List<ProjectProcess> listByProjectId = projectProcessDao.getListByProjectId(projectId);

            //通过task计算项目进度
            countProcessBytask(project,listByProjectId);

            project.setUsers(projectDao.getUsers(projectId));
            String processStr = "";
            for (ProjectProcess p: listByProjectId) {
                processStr += ","+p.getType().getCode();
            }
            if(processStr.length()>0) {
                processStr = processStr.substring(1, processStr.length());
            }
            project.setProcess(processStr);

            //翻译userid
            User user = userDao.selectById(project.getCreateBy());
            if (user!=null){
                if (user.getNickName() != null) {
                    project.setCreateByName(user.getNickName());
                } else {
                    project.setCreateByName(user.getUsername());
                }

            }
            return project;
        }

        return null;
    }

    @Override
    public List<Project> getList(Project project) {
        List<Project> list=projectDao.getList(project);
        return null;
    }

    @Override
    public IPage<Project> getPage(ProjectParamVo projectParam, Page<Project> page, List<String> userStrs) {
        //创建查询条件
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
//        name     progress  dueTime
        if(projectParam.getOrderByName()!=null && projectParam.getOrderByName()){
            if (projectParam.getIsAsc()) {
                queryWrapper.orderByAsc("name");
            } else {
                queryWrapper.orderByDesc("name");
            }
        }
        if(projectParam.getOrderByProgress()!= null && projectParam.getOrderByProgress()){
            if (projectParam.getIsAsc()) {
                queryWrapper.orderByAsc("progress");
            } else {
                queryWrapper.orderByDesc("progress");
            }
        }
        if(projectParam.getOrderByDueTime() !=null && projectParam.getOrderByDueTime()){
            if (projectParam.getIsAsc()) {
                queryWrapper.orderByAsc("due_time");
            } else {
                queryWrapper.orderByDesc("due_time");
            }
        }
        queryWrapper.orderByDesc("create_time");//正序，降序
        queryWrapper.eq("is_del",0);
        queryWrapper.in("create_by",userStrs);
        IPage<Project> iPage = projectDao.selectPage(page,queryWrapper);
        List<Project> projectList = iPage.getRecords();
        //查询项目相关进度和人员
        for (Project project: projectList) {
            List<ProjectProcess> list = projectProcessDao.getListByProjectId(project.getProjectId());

            //通过task计算项目进度
            countProcessBytask(project,list);
//            List<String> userList = projectDao.getUsers(project.getProjectId());
            List<User> userList = userDao.getUserByProjectId(project.getProjectId());
            //翻译userName没有昵称返回username
            List<String> userNameString = new ArrayList<>();
            for (User u: userList) {
                if (u.getNickName() != null) {
                    userNameString.add(u.getNickName());
                } else {
                    userNameString.add(u.getUsername());
                }
            }
            project.setUsers(userNameString);
            String createBy = project.getCreateBy();
            project.setCreateBy(projectDao.getCreateByName(createBy));
        }
        return iPage;
    }

    @Transactional
    @Override
    public int update(Project project) {
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("project_id", project.getProjectId());
        projectProcessDao.deleteByMap(columnMap);
        batchInsertProjectProcess(project);

        return projectDao.update(project);
    }

    @Override
    public int updateProjectInfo(Project project) {
        project.setUpdateTime(new Date());
        return projectDao.update(project);
    }

    //提取共用方法，批量插入ProjectProcess
    private void batchInsertProjectProcess(Project project){
        String processStr = project.getProcess();
        if(processStr!=null){
            String[] sp = processStr.split(",");
            for (String p:sp) {
                ProjectProcess projectProcess = new ProjectProcess();
                projectProcess.setProjectId(project.getProjectId());
                projectProcess.setType(ProjectProcessTypesEnum.getByCode(Integer.parseInt(p)));
                projectProcess.setCreateTime(project.getCreateTime());
                projectProcessDao.insert(projectProcess);
            }
        }
    }

    //通过task计算项目进度
    private void countProcessBytask(Project project,List<ProjectProcess> list){
        Long handSumP=0L;
        Long totalSumP=0L;
        for(ProjectProcess p :list){
            QueryWrapper<Task> taskQueryWrapper = new QueryWrapper<>();
            taskQueryWrapper.eq("is_del",0);
            taskQueryWrapper.eq("project_id",p.getProjectId());
            taskQueryWrapper.eq("type",p.getType().getCode());
            List<Task> tasks = taskDao.selectList(taskQueryWrapper);
            Long handSum=0L;
            Long totalSum=0L;
            for (Task t:tasks) {
                handSum += t.getHandledCount();
                totalSum += t.getTatalCount();
            }
            p.setHandledCount(handSum.toString());
            p.setTatalCount(totalSum.toString());
            System.out.println(totalSum!=0);
            if(totalSum!=0){
                p.setProgress( (double)handSum / totalSum);
            }
            handSumP += handSum;
            totalSumP += totalSum;
            if(totalSumP!=0){
                project.setProgress((double)handSumP / totalSumP);
            }

        }
        project.setProjectProgress(list);
    }

}
