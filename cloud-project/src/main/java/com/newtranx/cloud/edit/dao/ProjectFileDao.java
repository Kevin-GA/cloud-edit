package com.newtranx.cloud.edit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newtranx.cloud.edit.entities.Project;
import com.newtranx.cloud.edit.entities.ProjectFile;
import com.newtranx.cloud.edit.entities.ProjectProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProjectFileDao extends BaseMapper<ProjectFile>
{
    int create(@Param("entity") ProjectFile projectFile);

    int update(@Param("entity") ProjectFile projectFile);

    List<ProjectFile> getListByProjectId(@Param("projectId") Long projectId);

    List<ProjectFile> getUnassignFilesByProjectId(@Param("projectId") Long projectId);

    List<ProjectFile> getEditAndCheckFilesByProjectId(@Param("projectId") Long projectId);

    @Select("        select * from project_file pf where pf.is_del=0 and pf.file_id in(" +
            "            select t.file_id from task t where" +
            "            t.type != #{type}" +
            "            and t.is_del=0" +
            "            and t.project_id=#{projectId}" +
            " ) ")
    List<ProjectFile> getFilesByProjectIdAnd(@Param("projectId") Long projectId,@Param("type")String type);

    List<ProjectFile> getFilesByProjectIdNotInFileIds(@Param("projectId") Long projectId,@Param("list") List<Long> fileIds);
}
