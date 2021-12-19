package com.newtranx.task.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.newtranx.task.base.BaseController;
import com.newtranx.task.base.BaseUser;
import com.newtranx.task.commons.response.ResponseCode;
import com.newtranx.task.commons.response.ResponseResult;
import com.newtranx.task.commons.utils.PageData;
import com.newtranx.task.domain.Task;
import com.newtranx.task.dto.TaskR;
import com.newtranx.task.dto.TeamUser;
import com.newtranx.task.dto.TeamUserR;
import com.newtranx.task.mapper.TeamUserMapper;
import com.newtranx.task.service.ITaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author AHui
 * @since 2021-01-29
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/task/")
@Api(value = "API - TaskController", description = "任务接口")
public class TaskController extends BaseController {
    @Resource
    private ITaskService taskService;

    /**
     * 新增
     *
     * @param task {@link Task}
     * @return {@link ResponseResult}
     */
    @PostMapping("create")
    @PreAuthorize("hasPermission('/home/task', 'task:task') ")
    @ApiOperation(value = "新增")
    public ResponseResult create(@Valid @RequestBody Task task, BindingResult bindingResult) {
        // 表单验证
        if (bindingResult.hasErrors()) {
            return ResponseResult.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        // 业务逻辑
        //task.setUserId(Long.decode(getUserId().toString()));
        boolean created = taskService.create(task);
        if (created) {
            return ResponseResult.success("创建成功");
        }

        return ResponseResult.failure(ResponseCode.INTERFACE_ADDRESS_INVALID);
    }

    /**
     * 删除
     *
     * @param taskId {@code Long}
     * @return {@link ResponseResult}
     */
    @DeleteMapping("remove/{taskId}")
    @PreAuthorize("hasPermission('/home/task', 'task:task') ")
    @ApiOperation(value = "根据主键删除")
    public ResponseResult remove(@PathVariable Long taskId) {
        // 业务逻辑
        boolean deleted = taskService.remove(taskId);
        if (deleted) {
            return ResponseResult.success("删除成功");
        }

        return ResponseResult.failure(ResponseCode.INTERFACE_ADDRESS_INVALID);
    }

    /**
     * 修改
     *
     * @param task {@link Task}
     * @return {@link ResponseResult}
     */
    @PutMapping("update")
    @PreAuthorize("hasPermission('/home/task', 'task:task') ")
    @ApiOperation(value = "修改")
    public ResponseResult update(@Valid @RequestBody Task task, BindingResult bindingResult) {
        // 表单验证
        if (bindingResult.hasErrors()) {
            return ResponseResult.failure(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        // 业务逻辑
        //task.setUserId(Long.decode(getUserId().toString()));
        boolean updated = taskService.update(task);
        if (updated) {
            return ResponseResult.success("编辑成功");
        }

        return ResponseResult.failure(ResponseCode.INTERFACE_ADDRESS_INVALID);
    }

    /**
     * 获取
     *
     * @param taskId {@code Long}
     * @return {@link ResponseResult}
     */
    @GetMapping("get/{taskId}")
    @ApiOperation(value = "根据主键查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true,
                    dataType = "string", paramType = "query")
    })
    public ResponseResult get(@PathVariable Long taskId) {
        Task task = taskService.get(taskId);
        return ResponseResult.success(task);
    }
    /**
     * 获取
     *
     * @param taskId {@code Long}
     * @return {@link ResponseResult}
     */
    @GetMapping("getAllInfo/{taskId}")
    @ApiOperation(value = "根据主键查询多表数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID", required = true,
                    dataType = "string", paramType = "query")
    })
    public ResponseResult getAllInfo(@PathVariable Long taskId) {
        TaskR task = taskService.getAllInfo(taskId);
        return ResponseResult.success(task);
    }
    /**
     * 分页
     *
     * @param current {@code int} 页码
     * @param size    {@code int} 笔数
     * @return {@link ResponseResult}
     */
    @GetMapping("page")
    @ApiOperation(value = " 根据实体属性分页查询")
    public ResponseResult page(
            @RequestParam int current, @RequestParam int size, @ModelAttribute Task task) {
        BaseUser baseUser = getBaseUser();
        boolean admin = baseUser.isAdmin();
        if(!admin){
            task.setUserId(Long.decode(getUserId().toString()));
        }else{

            List<TeamUser> teamUsers = taskService.selectUserIdsByUserId(Long.decode(getUserId().toString()));
            StringBuffer stringBuffer = new StringBuffer();
            for(TeamUser teamUser:teamUsers){
                //stringBuffer.append("'");
                stringBuffer.append(teamUser.getUserId());
                //stringBuffer.append("'");
                stringBuffer.append(",");
            }
            String userIds= stringBuffer!=null?stringBuffer.toString().substring(0, stringBuffer.toString().length() -1):null;
            task.setUserIds(userIds);
        }
        //task.setUserId(Long.decode(getUserId().toString()));
        IPage<TaskR> page = taskService.listByQuery(current, size, task);
        return ResponseResult.success(page);
    }
    /**
     * 获取
     *
     * @param teamId {@code Long}
     * @return {@link ResponseResult}
     */
    @GetMapping("getTeamUser/{teamId}")
    @ApiOperation(value = "根据teamId查询当前任务所有的参与人员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teamId", value = "teamId", required = true,
                    dataType = "string", paramType = "query")
    })
    public ResponseResult getTeamUser(@PathVariable Long teamId) {
        List<TeamUserR> teamUserR = taskService.selectTeamUser(teamId);
        return ResponseResult.success(teamUserR);
    }
}
