package com.newtranx.cloud.edit.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.newtranx.cloud.edit.dao.ProjectProcessDao;
import com.newtranx.cloud.edit.dao.TaskDao;
import com.newtranx.cloud.edit.entities.ProjectProcess;
import com.newtranx.cloud.edit.entities.Task;
import com.newtranx.cloud.edit.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @Author: niujiaxin
 * @Date: 2021-02-05 01:10
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private ProjectProcessDao projectProcessDao;

    @Override
    public int assign(Task task) {
        int res = 0;
        if(task.getTaskId()!=null)
            res = taskDao.updateById(task);
        else
            res= taskDao.create(task);
        return res;
    }

    @Override
    public int delTaskByFileId(Long fileId) {
        LambdaUpdateWrapper<Task> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Task::getFileId, fileId).set(Task::getIsDel, 1);
        Task task = new Task();
        return taskDao.update(task, lambdaUpdateWrapper);
    }

    @Override
    public int delTaskByProjectId(Long projectId) {
        LambdaUpdateWrapper<Task> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Task::getProjectId, projectId).set(Task::getIsDel, 1);
        Task task = new Task();
        return taskDao.update(task, lambdaUpdateWrapper);
    }

    @Override
    public List<String> getReduceProcess(List<Long> fileIds, Long projectId){
        //查询定义的工作流程
        List<ProjectProcess> projectProcessList = projectProcessDao.getListByProjectId(projectId);
        List<String> listMap = new ArrayList<>();
        for(ProjectProcess pp:projectProcessList){
            listMap.add(pp.getType().getName());
        }
//        listMap.add("翻译");
//        listMap.add("编辑");
//        listMap.add("校对");
        //根据文件id获取task
        List<List<String>> arrayLists = new ArrayList<>();
        for (Long fileId:fileIds) {
            arrayLists.add(taskDao.getTasksString(fileId));
        }
        //不同task的type取并集
        List<String> listAll = arrayLists.get(0).parallelStream().collect(toList());
        for (List<String> list:arrayLists) {
            List<String> listAll2 = list.parallelStream().collect(toList());
            listAll.addAll(listAll2);
        }
        //与预定义工作流程取差集获取未分配的类型
        List<String> reduce = listMap.stream().filter(item -> !listAll.contains(item)).collect(toList());

        return reduce;
    }

    @Override
    public List<Task> getTasksByProjectId(Long projectId) {
        return null;
    }
}
