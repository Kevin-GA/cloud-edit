package com.newtranx.cloud.edit.service.impl;

import com.newtranx.cloud.edit.controller.ProjectController;
import com.newtranx.cloud.edit.dao.ProjectFileDao;
import com.newtranx.cloud.edit.dao.TaskDao;
import com.newtranx.cloud.edit.dto.ProjectFileVo;
import com.newtranx.cloud.edit.entities.ProjectFile;
import com.newtranx.cloud.edit.entities.Task;
import com.newtranx.cloud.edit.service.ProjectFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: niujiaxin
 * @Date: 2021-02-04 23:11
 */

@Service("projectFileService")
public class ProjectFileServiceImpl implements ProjectFileService {

    @Autowired
    private ProjectFileDao projectFileDao;

    @Autowired
    private TaskDao taskDao;

    @Override
    public Object uploadFile(MultipartFile file) {
        return null;
    }

    @Override
    public List<ProjectFileVo> getFilesByProjectId(Long projectId) {
        List<ProjectFileVo> projectFileVos = new ArrayList<>();
        //获取项目关联文件
        List<ProjectFile> fileList = projectFileDao.getListByProjectId(projectId);
        for (ProjectFile projectFile:fileList){
            ProjectFileVo projectFileVo = new ProjectFileVo();
            //查询每个文件对应的任务分配的人
            List<Task> tasks = taskDao.getTasksByFileId(projectFile.getFileId());
            projectFileVo.setTaskList(tasks);
            projectFileVo.setProjectFile(projectFile);
            projectFileVos.add(projectFileVo);
        }
        return projectFileVos;
    }

    @Override
    public List<ProjectFile> getUnAssignFilesByProjectId(Long projectId) {
        //获取项目、文件、聚合type
        List<Task> aggreTypeTasksByProjectId = taskDao.getAggreTypeTasksByProjectId(projectId);
        List<Long> fileIdConditions = new ArrayList<>();
        for (Task t: aggreTypeTasksByProjectId) {
            //提取已分配所有类型type的文件ID
            if(t.getType().equals("1,2,3"))
                fileIdConditions.add(t.getFileId());
        }
        if(fileIdConditions.size()>0) {
            //剔除完全分配的文件
            return projectFileDao.getFilesByProjectIdNotInFileIds(projectId, fileIdConditions);
        }else {
            return projectFileDao.getListByProjectId(projectId);

        }
    }

    @Override
    public List<ProjectFile> getEditAndCheckFilesByProjectId(Long projectId) {
        return projectFileDao.getEditAndCheckFilesByProjectId(projectId);
    }

    @Override
    public int addFile(ProjectFile projectFile) {
        return projectFileDao.create(projectFile);
    }

    @Override
    public int updateFile(ProjectFile projectFile) {
        return projectFileDao.updateById(projectFile);
    }

    @Override
    public int delFile(Long fileId) {
        ProjectFile projectFile = new ProjectFile();
        projectFile.setFileId(fileId);
        projectFile.setIsDel(1);
        return updateFile(projectFile);
    }

    @Override
    public List<Task> getTaskList(Long fileId) {
        return null;
    }

    @Override
    public ProjectFile getProjectFileByFileId(Long fileId) {
        return projectFileDao.selectById(fileId);
    }
}
