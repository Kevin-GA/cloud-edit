package com.newtranx.cloud.edit.service;


import com.newtranx.cloud.edit.mongodb.ProjectTranslateFile;

/***
 * 任务service
 * @author 佟文森
 */
public interface TaskService {

    Boolean addProjectTranslateFileHistory(ProjectTranslateFile projectTranslateFile, String userId, String userNikeName, String describe);

}
