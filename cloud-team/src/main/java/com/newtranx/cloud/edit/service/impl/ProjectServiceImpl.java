package com.newtranx.cloud.edit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newtranx.cloud.edit.dao.ProjectMapper;
import com.newtranx.cloud.edit.entities.Project;
import com.newtranx.cloud.edit.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * 任务service
 * @author 佟文森
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

}
