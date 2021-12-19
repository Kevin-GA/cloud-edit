package com.newtranx.cloud.edit.service.impl;

import com.newtranx.cloud.edit.mongodb.ProjectTranslateFile;
import com.newtranx.cloud.edit.mongodb.ProjectTranslateFileHistory;
import com.newtranx.cloud.edit.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/***
 * 任务service
 * @author 佟文森
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /****
     * 添加修改记录
     * @param projectTranslateFile
     * @param describe
     * @return
     */
    public Boolean addProjectTranslateFileHistory(ProjectTranslateFile projectTranslateFile, String userId, String userNikeName, String describe) {
        ProjectTranslateFileHistory ptfh = new ProjectTranslateFileHistory();
        ptfh.setTranslateId(projectTranslateFile.getId());
        ptfh.setProjectId(projectTranslateFile.getProjectId());
        ptfh.setFileId(projectTranslateFile.getFileId());
        ptfh.setSequenceId(projectTranslateFile.getSequenceId());
        ptfh.setOriginalText(projectTranslateFile.getOriginalText());
        ptfh.setTranslateText(projectTranslateFile.getTranslateText());
        ptfh.setTotal(projectTranslateFile.getTotal());
        ptfh.setTranslatedCount(projectTranslateFile.getTranslatedCount());
        ptfh.setCurrentState(projectTranslateFile.getCurrentState());
        ptfh.setCurrentProgress(projectTranslateFile.getCurrentProgress());
        ptfh.setTranslateType(projectTranslateFile.getTranslateType());
        ptfh.setRemarks(projectTranslateFile.getRemarks());
        ptfh.setDescribe(describe);
        ptfh.setUserId(userId);
        ptfh.setUserNikeName(userNikeName);
        ptfh.setCreateDate(new Date());
        mongoTemplate.insert(ptfh);
        return true;
    }

}
