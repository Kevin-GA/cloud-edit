package com.newtranx.cloud.edit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newtranx.cloud.edit.dto.TaskDto;
import com.newtranx.cloud.edit.dto.UserTaskListDto;
import com.newtranx.cloud.edit.entities.Task;
import com.newtranx.cloud.edit.entities.TeamUser;

import java.util.List;

/***
 * 团队成员service
 * @author 佟文森
 */
public interface TaskService extends IService<Task> {

    List<UserTaskListDto> getUserTaskList(TaskDto taskDto);

    int getUserTaskCount(TaskDto taskDto);

    /**
     * 查询管理员团队下的所有成员
     * @param userId
     * @return
     */
    List<TeamUser> selectUserIdsByUserId(Long userId);
}
