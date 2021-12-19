package com.newtranx.cloud.edit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newtranx.cloud.edit.entities.ProjectFile;
import com.newtranx.cloud.edit.entities.ProjectProcess;
import com.newtranx.cloud.edit.entities.Task;
import com.newtranx.cloud.edit.entities.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskDao extends BaseMapper<Task>
{

    int create(@Param("entity") Task task);

//    int update(@Param("entity") Task task);

    List<Task> getTasksByFileId(@Param("fileId") Long fileId);

    List<Task> getTasksByFileIds(@Param("list") List<Long> fileIds);

    List<String> getTasksString(@Param("fileId") Long fileId);

    List<Task> getSumTasks(@Param("projectId") Long projectId,@Param("list") List<Long> fileIds);

    @Select(" select project_id,`type`, sum(handled_count) as handled_count, sum(tatal_count) as tatal_count from task" +
            " where  is_del = 0 and project_id = #{projectId}" +
            " group by project_id,`type`")
    List<Task> getSumTasksByProjectId(@Param("projectId") Long projectId);

    @Select(" select project_id,`type`, sum(handled_count) as handled_count, sum(tatal_count) as tatal_count from task" +
            " where  is_del = 0 and project_id = #{projectId}" +
            " group by project_id,`type`")
    List<Task> getSumTasksByProjectIdAndType(@Param("projectId") Long projectId);

    //获取聚合任务类型task
    @Select("SELECT  GROUP_CONCAT(distinct(t.type)) type,t.project_id,t.file_id FROM task t " +
            "WHERE t.project_id= #{projectId}  group by t.project_id, t.file_id")
    List<Task> getAggreTypeTasksByProjectId(@Param("projectId") Long projectId);

}
