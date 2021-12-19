package com.newtranx.cloud.edit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newtranx.cloud.edit.dto.TaskDto;
import com.newtranx.cloud.edit.dto.UserTaskListDto;
import com.newtranx.cloud.edit.entities.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/***
 * 任务Mapper
 * @author 佟文森
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

    List<UserTaskListDto> getUserTaskList(TaskDto taskDto);

    int getUserTaskCount(TaskDto taskDto);
}
