package com.newtranx.cloud.edit.service;

import com.newtranx.cloud.edit.entities.Task;

import java.util.List;

/**
 * @Author: niujiaxin
 * @Date: 2021-02-05 11:43
 */
public interface TaskService {

    int assign(Task task);

    int delTaskByFileId(Long fileId);

    int delTaskByProjectId(Long projectId);

    List<String> getReduceProcess(List<Long> fileIds , Long projectId);

    List<Task> getTasksByProjectId(Long projectId);

}
