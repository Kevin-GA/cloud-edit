package com.newtranx.cloud.edit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newtranx.cloud.edit.base.BaseController;
import com.newtranx.cloud.edit.common.entities.Result;
import com.newtranx.cloud.edit.common.enums.FailureCodeEnum;
import com.newtranx.cloud.edit.dto.PageUserTaskListDto;
import com.newtranx.cloud.edit.dto.TaskDto;
import com.newtranx.cloud.edit.entities.Task;
import com.newtranx.cloud.edit.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

/***
 * 任务相关接口
 * @author 佟文森
 */
@Api(value = "任务接口")
@RestController
@Slf4j
public class TaskController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;

    @ApiOperation(value = "getUserTaskList", notes = "我的团队模块-成员任务列表")
    @ApiResponses({@ApiResponse(code = 200, message = "成员任务列表", response = Result.class)})
    @PostMapping(value = "/getUserTaskList")
    public Result getUserTaskList(@RequestBody TaskDto taskDto) {
        taskDto.setUserId(new BigInteger(getUserId().toString()));
        //TODO 先假设由前端传入团队成员用户ID
        if (taskDto.getUserId() == null) {
            //缺少参数 团队成员的用户ID 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (taskDto.getPageIndex() == null) {
            //缺少参数 页数 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        //TODO 先假设由前端传入，后面具体对接时确认方案，加入默认值
        if (taskDto.getPageSize() == null) {
            //TODO 产品定义 每页20条 后面具体对接时确认是否可以修改page封装将20条作为默认
            taskDto.setPageSize(20);
        }
        QueryWrapper<Task> queryWrapper = new QueryWrapper();
        //为空查询全部 不为空按条件查询
        if(taskDto.getStatus() != null){
            //任务状态不为空 作为查询条件加入
            queryWrapper.lambda().eq(Task::getStatus, taskDto.getStatus());
        }
        //组装UserId
        queryWrapper.lambda().eq(Task::getUserId,taskDto.getUserId());
        //只能看到未被删除的任务
        queryWrapper.lambda().eq(Task::getIsDel,0);
        PageUserTaskListDto pageUserTaskListDto = new PageUserTaskListDto();
        pageUserTaskListDto.setTotal(taskService.count(queryWrapper));
        pageUserTaskListDto.setUserTaskList(taskService.getUserTaskList(taskDto));
        //返回给前端
        return Result.getSuccessResult(pageUserTaskListDto);
    }

}

