package com.newtranx.cloud.edit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newtranx.cloud.edit.dto.ProjectParamVo;
import com.newtranx.cloud.edit.entities.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther Charlie
 * @create 2021-02-01 05:40
 */
public interface ProjectService
{
    int create(Project project);

    int del(@Param("projectId") Long projectId);

    Project getProjectById(@Param("projectId") Long projectId);

    List<Project> getList(Project project);

    IPage<Project> getPage(ProjectParamVo projectParam, Page<Project> page, List<String> userStrs);

    int update(Project project);

    int updateProjectInfo(Project project);
}
