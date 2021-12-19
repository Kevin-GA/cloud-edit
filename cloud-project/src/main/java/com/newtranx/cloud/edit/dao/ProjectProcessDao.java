package com.newtranx.cloud.edit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newtranx.cloud.edit.entities.Project;
import com.newtranx.cloud.edit.entities.ProjectProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectProcessDao extends BaseMapper<ProjectProcess>
{

    public List<ProjectProcess> getListByProjectId(@Param("projectId")Long projectId);


}