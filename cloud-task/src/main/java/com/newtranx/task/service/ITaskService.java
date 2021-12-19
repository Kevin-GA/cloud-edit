package com.newtranx.task.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.newtranx.task.commons.utils.PageData;
import com.newtranx.task.domain.Task;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newtranx.task.dto.TaskR;
import com.newtranx.task.dto.TeamUser;
import com.newtranx.task.dto.TeamUserR;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author AHui
 * @since 2021-01-29
 */
public interface ITaskService extends IService<Task> {
    /**
     * 新增
     *
     * @param task {@link Task}
     * @return {@code boolean}
     */
    boolean create(Task task);

    /**
     * 删除
     *
     * @param id {@code Long}
     * @return {@code boolean}
     */
    boolean remove(Long id);

    /**
     * 编辑
     *
     * @param task {@link Task}
     * @return {@code boolean}
     */
    boolean update(Task task);

    /**
     * 获取
     *
     * @param id {@code Long}
     * @return {@link Task}
     */
    Task get(Long id);
    /**
     * 获取
     *
     * @param id {@code Long}
     * @return {@link Task}
     */
    TaskR getAllInfo(Long id);
    /**
     * 分页
     *
     * @param current {@code int} 页码
     * @param size    {@code int} 笔数
     * @param task    {@link Task}
     * @return {@code IPage<Task>}
     */
    IPage<Task> page(int current, int size, Task task);
    /**
     * 分页
     *
     * @param current  {@code int} 页码
     * @param size     {@code int} 笔数
     * @param task {@link Task}
     * @return {@code IPage<TeamUser>}
     */
    IPage<TaskR> listByQuery(int current, int size, Task task);

    /**
     * 查询当前任务所有参与人员
     * @param teamId
     * @return
     */
    List<TeamUserR> selectTeamUser(Long teamId);

    /**
     * 查询管理员团队下的所有成员
     * @param userId
     * @return
     */
    List<TeamUser> selectUserIdsByUserId(Long userId);
}
