package com.newtranx.cloud.edit.service;

import com.newtranx.cloud.edit.dto.ProjectFileVo;
import com.newtranx.cloud.edit.entities.ProjectFile;
import com.newtranx.cloud.edit.entities.Task;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: niujiaxin
 * @Date: 2021-02-02 08:52
 */
public interface ProjectFileService {

    Object uploadFile(MultipartFile file);

    List<ProjectFileVo> getFilesByProjectId(Long projectId);

    List<ProjectFile> getUnAssignFilesByProjectId(Long projectId);

    List<ProjectFile> getEditAndCheckFilesByProjectId(Long projectId);

    int addFile(ProjectFile projectFile);

    int updateFile(ProjectFile projectFile);

    int delFile(Long fileId);

    List<Task> getTaskList(Long fileId);

    ProjectFile getProjectFileByFileId(Long fileId);
}
