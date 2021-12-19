package com.newtranx.cloud.edit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.newtranx.cloud.edit.entities.Project;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProjectDao extends BaseMapper<Project>
{
    public int create(@Param("entity") Project project);

    public Project getProjectById(@Param("projectId") Long projectId);

    public List<Project> getList(Project project);

    public Integer isdelByProjectId(@Param("projectId") Long projectId);

    public List<String> getUsers(@Param("projectId") Long projectId);

    int update(@Param("entity") Project project);

    @Select("select username from user where user_id= #{createBy}")
    String getCreateByName(String createBy);

}
