package com.newtranx.task.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.newtranx.task.commons.utils.PageData;
import com.newtranx.task.domain.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newtranx.task.dto.TaskR;
import com.newtranx.task.dto.TeamUserR;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author AHui
 * @since 2021-01-29
 */
public interface TaskMapper extends BaseMapper<Task> {
    /**
     * 分页查询
     */
    IPage<TaskR> listByQuery(IPage<Task> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    TaskR getAllInfo(@Param(Constants.WRAPPER) Wrapper wrapper);
}
