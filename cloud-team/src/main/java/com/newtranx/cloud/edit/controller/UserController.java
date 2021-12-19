package com.newtranx.cloud.edit.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newtranx.cloud.edit.common.entities.Result;
import com.newtranx.cloud.edit.common.enums.FailureCodeEnum;
import com.newtranx.cloud.edit.dto.UserQuery;
import com.newtranx.cloud.edit.entities.TeamUser;
import com.newtranx.cloud.edit.entities.User;
import com.newtranx.cloud.edit.service.TeamUserService;
import com.newtranx.cloud.edit.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 用户接口
 * @author 佟文森
 */
@Api(value = "用户接口")
@RestController
@Slf4j
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    TeamUserService teamUserService;

    /***
     * @author 佟文森
     * @param userQuery 将手机号或昵称（前端输入的字符串）传递至参数 mobileOrNickName
     * @return user 返回用户信息
     */
    @ApiOperation(value = "queryUserByMobileOrNikename", notes = "添加成员模块-手机号或昵称查询")
    @ApiResponses({@ApiResponse(code = 200, message = "用户对象", response = User.class)})
    @PostMapping(value = "/queryUserByMobileOrNikename")
    public Result queryUserByMobileOrNikename(@RequestBody UserQuery userQuery) {
        //验证参数
        if (!StringUtils.isNoneBlank(userQuery.getMobileOrNickName())) {
            //缺少参数 返回参数不正确
            LOG.info("缺少参数 返回参数不正确");
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (!StringUtils.isNoneBlank(userQuery.getTeamId())) {
            //缺少参数 返回参数不正确
            LOG.info("缺少参数 返回参数不正确");
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        //判断是否都是数字，因为现在手机号多变，可以肯定都是数字就可以拿去查询手机号
        //正则 是否全是数字
        //TODO 考虑昵称是否会出现都是数字 @产品经理
        Pattern pattern = Pattern.compile("[0-9]*");
        //验证正则
        Matcher isNumber = pattern.matcher(userQuery.getMobileOrNickName());
        //创建查询构造器 用来组装查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        //判断正则结果
        //TODO 目前昵称会出现全是数字
        if (isNumber.matches()) {
            //全是数字 组装查询条件 手机号
            LOG.info("全是数字 组装查询条件 手机号_:" + userQuery.getMobileOrNickName());
            System.out.println();
            queryWrapper.lambda().eq(User::getMobile, userQuery.getMobileOrNickName());
        } else {
            //不全是数字 组装查询条件 昵称
            LOG.info("不全是数字 组装查询条件 昵称_:" + userQuery.getMobileOrNickName());
            queryWrapper.lambda().eq(User::getNickName, userQuery.getMobileOrNickName());
        }
        //调用通用service封装的查询一条数据
        User users = userService.getOne(queryWrapper);
        LOG.info("查到用户信息_:" + users);
        if (users != null) {
            QueryWrapper<TeamUser> queryTeamWrapper = new QueryWrapper();
            //统计该团队下 该用户是否已添加
            queryTeamWrapper.lambda().eq(TeamUser::getUserId,users.getUserId());
            queryTeamWrapper.lambda().eq(TeamUser::getTeamId,userQuery.getTeamId());
            queryTeamWrapper.lambda().eq(TeamUser::getIsDel,0);
            int userCount = teamUserService.count(queryTeamWrapper);
            if(userCount > 0){
                //该人员已添加
                return Result.getSuccessResult("true","该人员已添加",users);
            }else{
                return Result.getSuccessResult("false","该人员未添加",users);;
            }
        }
        //未查到数据
        //TODO 没有空数据的状态 先使用参数不正确的错误状态
        return Result.getSuccessResult();
    }

}

