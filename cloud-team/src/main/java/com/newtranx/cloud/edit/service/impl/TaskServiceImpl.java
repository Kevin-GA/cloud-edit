package com.newtranx.cloud.edit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newtranx.cloud.edit.dao.TaskMapper;
import com.newtranx.cloud.edit.dao.TeamUserMapper;
import com.newtranx.cloud.edit.dto.TaskDto;
import com.newtranx.cloud.edit.dto.UserTaskListDto;
import com.newtranx.cloud.edit.entities.Task;
import com.newtranx.cloud.edit.entities.TeamUser;
import com.newtranx.cloud.edit.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * 任务service
 * @author 佟文森
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    TeamUserMapper teamUserMapper;

    @Override
    public List<UserTaskListDto> getUserTaskList(TaskDto taskDto) {
        return taskMapper.getUserTaskList(taskDto);
    }

    @Override
    public int getUserTaskCount(TaskDto taskDto) {
        return taskMapper.getUserTaskCount(taskDto);
    }

    @Override
    public List<TeamUser> selectUserIdsByUserId(Long userId) {
        return teamUserMapper.getTeamUserInfo(userId);
    }
}
