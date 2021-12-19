package com.newtranx.cloud.edit.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newtranx.cloud.edit.base.BaseController;
import com.newtranx.cloud.edit.common.entities.Result;
import com.newtranx.cloud.edit.common.enums.FailureCodeEnum;
import com.newtranx.cloud.edit.common.util.HttpUtil;
import com.newtranx.cloud.edit.dto.*;
import com.newtranx.cloud.edit.entities.TeamUser;
import com.newtranx.cloud.edit.entities.UserRole;
import com.newtranx.cloud.edit.mongodb.ProjectTranslateFile;
import com.newtranx.cloud.edit.mongodb.ProjectTranslateFileHistory;
import com.newtranx.cloud.edit.service.TaskFeignService;
import com.newtranx.cloud.edit.service.TaskService;
import com.newtranx.cloud.edit.service.TeamUserService;
import com.newtranx.cloud.edit.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private MongoTemplate mongoTemplate;

    @Autowired
    private TaskService taskService;

    @Autowired
    TeamUserService teamUserService;

    @Autowired
    TaskFeignService taskFeignService;

    @ApiOperation(value = "addtest")
    @PostMapping(value = "/addtest")
    public Result addtest(@RequestBody ProjectTranslateDto projectTranslateDto) {
        for(int i = 0; i< 20; i++){
            ProjectTranslateFile a = new ProjectTranslateFile();
            a.setFileId("1");
            a.setProjectId("1");
            a.setCurrentProgress("0.00");
            a.setOriginalText("原文"+i + "abscdawdawdwdwd  dawd dawd adwa ");
            a.setTranslateText("译文"+i+"译文");
            a.setTranslateType(1);
            a.setCurrentState(1);
            a.setSequenceId(i+"");
            mongoTemplate.save(a);
        }
        return Result.getSuccessResult();
    }

    @ApiOperation(value = "updateFileProgressList", notes = "任务模块-批量修改，原文，译文")
    @ApiResponses({@ApiResponse(code = 200, message = "Result", response = Result.class)})
    @PostMapping(value = "/updateFileProgressList")
    public Result updateFileProgressList(@RequestBody ProjectTranslateDto projectTranslateDto) {
        projectTranslateDto.setUserId(getUserId().toString());
        //TODO 先假设当前端能提供当前FileID
        if (projectTranslateDto.getTextList() == null || projectTranslateDto.getTextList().size() == 0) {
            //缺少参数 缺少修改集合的参数
            LOG.info("[TaskController.updateFileProgressList] 缺少参数 数据集合");
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        //需要确认权限 用户ID必传
        if (projectTranslateDto.getUserId() == null) {
            //缺少参数 文件ID 返回参数不正确
            LOG.info("[TaskController.updateFileProgressList] 缺少参数 用户ID 返回参数不正确");
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (!StringUtils.isNoneBlank(projectTranslateDto.getUserNikeName())) {
            //缺少参数 昵称 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        List<TextListDto> textList = projectTranslateDto.getTextList();
        //使用普通的循环处理 forEach + lambda 不方便处理逻辑
        for(int i = 0; i< textList.size(); i++){
            if(!textList.get(i).getOriginalText().equals("")){
                LOG.info("[TaskController.updateFileProgressList]  有原文内容");
                QueryWrapper<TeamUser> queryWrapper = new QueryWrapper();
                //组装查询参数 userId
                queryWrapper.lambda().eq(TeamUser::getUserId, projectTranslateDto.getUserId());
                queryWrapper.lambda().eq(TeamUser::getIsDel,"0");
                //查询这个用户的角色
                boolean userRole = false;
                List<TeamUser> teamUserList = teamUserService.list(queryWrapper);
                for(TeamUser tu : teamUserList){
                    if(Integer.parseInt(tu.getRoleId()) == 2){
                        userRole = true;
                    }
                }
                if(!userRole){
                    //只有项目经理可以修改 不是项目经理返回错误
                    LOG.info("[TaskController.updateFileProgressList]  用户不是项目经理 传递了原文修改参数 返回错误");
                    return Result.getFailureResult(FailureCodeEnum.SERVICE_EXCEPTION.getCode(),"用户不是项目经理，不能修改原文");
                }
                break;
            }
        }
        //循环修改
        for(int p = 0; p< textList.size(); p++){
            String id = textList.get(p).getTextId();
            log.info("id_:" + id);
            Query query = new Query(Criteria.where("_id").is(id));
            //修改内容对象构造器
            Update update = new Update();
            if(StringUtils.isNoneBlank(textList.get(p).getOriginalText())){
                //原文不为空 修改原文
                update.set("originalText",textList.get(p).getOriginalText());
            }
            if(StringUtils.isNoneBlank(textList.get(p).getTranslateText())){
                //译文不为空 修改译文
                update.set("translateText",textList.get(p).getTranslateText());
            }
            //查询这条记录 TODO 用来保存修改历史
            taskService.addProjectTranslateFileHistory(mongoTemplate.findOne(query,ProjectTranslateFile.class),projectTranslateDto.getUserId(),projectTranslateDto.getUserNikeName(),"");
            //执行修改
            mongoTemplate.upsert(query, update, ProjectTranslateFile.class);
        }
        return Result.getSuccessResult();

    }

    @ApiOperation(value = "updateFileProgress", notes = "任务模块-修改，原文，译文，翻译类型，状态")
    @ApiResponses({@ApiResponse(code = 200, message = "Result", response = Result.class)})
    @PostMapping(value = "/updateFileProgress")
    public Result updateFileProgress(@RequestBody ProjectTranslateDto projectTranslateDto, @RequestHeader HttpHeaders httpHeaders) {
        projectTranslateDto.setUserId(getUserId().toString());
        //TODO 先假设当前端能提供当前FileID
        if (projectTranslateDto.getTextId() == null) {
            //缺少参数 内容ID 返回参数不正确
            LOG.info("[TaskController.updateFileProgress] 缺少参数 文件ID 返回参数不正确");
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        //需要确认权限 用户ID必传
        if (projectTranslateDto.getUserId() == null) {
            //缺少参数 用户ID 返回参数不正确
            LOG.info("[TaskController.updateFileProgress] 缺少参数 文件用户ID 返回参数不正确");
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        LOG.info("[TaskController.updateFileProgress] 参数_:" + projectTranslateDto.getUserNikeName());
        if (!StringUtils.isNoneBlank(projectTranslateDto.getUserNikeName())) {
            //缺少参数 昵称 返回参数不正确
            LOG.info("[TaskController.updateFileProgress] 缺少参数 用户昵称 返回参数不正确");
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if(StringUtils.isNoneBlank(projectTranslateDto.getOriginalText())){
            //TODO 先直接使用项目内的用户模块 上生产可以换成服务间的调用（短期内不换也可以）
            //传递了原文内容 判断权限
            //创建查询构造器 用来组装修改使用的查询条件
            QueryWrapper<TeamUser> queryWrapper = new QueryWrapper();
            //组装查询参数 userId
            queryWrapper.lambda().eq(TeamUser::getUserId, projectTranslateDto.getUserId());
            queryWrapper.lambda().eq(TeamUser::getIsDel,"0");
            //查询这个用户的角色
            boolean userRole = false;
            List<TeamUser> teamUserList = teamUserService.list(queryWrapper);
            for(TeamUser tu : teamUserList){
                if(Integer.parseInt(tu.getRoleId()) == 2){
                    userRole = true;
                }
            }
            if(!userRole){
                //只有项目经理可以修改 不是项目经理返回错误
                LOG.info("[TaskController.updateFileProgressList]  用户不是项目经理 传递了原文修改参数 返回错误");
                return Result.getFailureResult(FailureCodeEnum.SERVICE_EXCEPTION.getCode(),"用户不是项目经理，不能修改原文");
            }
        }
        Query query = new Query(Criteria.where("_id").is(projectTranslateDto.getTextId()));
        //修改内容对象构造器
        Update update = new Update();
        if(StringUtils.isNoneBlank(projectTranslateDto.getOriginalText())){
            //原文不为空 修改原文
            update.set("originalText",projectTranslateDto.getOriginalText());
        }
        if(StringUtils.isNoneBlank(projectTranslateDto.getTranslateText())){
            //译文不为空 修改译文
            update.set("translateText",projectTranslateDto.getTranslateText());
        }
        if(projectTranslateDto.getCurrentState() != null){
            //状态不为空 修改状态
            update.set("currentState",projectTranslateDto.getCurrentState());
        }
        if(projectTranslateDto.getTranslateType() != null){
            //译文类型不为空 修改类型
            update.set("translateType",projectTranslateDto.getTranslateType());
        }
        //执行修改
        mongoTemplate.upsert(query, update, ProjectTranslateFile.class);

        if(projectTranslateDto.getCurrentState() != null){
            //当前字段 状态确认 全都调用 查询所有已确认的字数 并调用项目模块 修改进度
            ProjectTranslateFile ptf = mongoTemplate.findById(projectTranslateDto.getTextId(),ProjectTranslateFile.class);
            Query queryProject = new Query();
            Criteria criteria = new Criteria();
            criteria.and("projectId").is(ptf.getProjectId());
            criteria.and("currentState").is(projectTranslateDto.getCurrentState());
            queryProject.addCriteria(criteria);
            List<ProjectTranslateFile> ptfList = mongoTemplate.find(queryProject,ProjectTranslateFile.class);
            if(ptfList != null && ptfList.size() > 0){
                Integer translatedCount = 0;
                for(ProjectTranslateFile ptfpo : ptfList){
                    translatedCount = translatedCount + ptfpo.getTotal();
                }
                TaskFeignDto taskFeignDto = new TaskFeignDto();
                taskFeignDto.setProjectId(Long.valueOf(ptf.getProjectId()));
                taskFeignDto.setUserId(Long.valueOf(projectTranslateDto.getUserId()));
                taskFeignDto.setFileId(Long.valueOf(ptf.getFileId()));
                taskFeignDto.setHandledCount(Long.valueOf(translatedCount));
                taskFeignDto.setType(projectTranslateDto.getCurrentState().toString());
                taskFeignService.updateProgress(taskFeignDto,httpHeaders);
            }
        }

        //查询这条记录 TODO 用来保存修改历史
        taskService.addProjectTranslateFileHistory(mongoTemplate.findOne(query,ProjectTranslateFile.class),projectTranslateDto.getUserId(),projectTranslateDto.getUserNikeName(),"");
        return Result.getSuccessResult();

    }

    @ApiOperation(value = "getProjectTranslateList", notes = "任务模块-详情页-内容列表（翻译 校对 编辑）")
    @ApiResponses({@ApiResponse(code = 200, message = "Result", response = Result.class)})
    @PostMapping(value = "/getProjectTranslateList")
    public Result getProjectTranslateList(@RequestBody TaskDto taskDto) {
        taskDto.setUserId(new BigInteger(getUserId().toString()));
        if (taskDto.getProjectId() == null) {
            //缺少参数 项目id 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        //TODO 假设任务列表含有文件id
        if (taskDto.getFileId() == null) {
            //缺少参数 文件id 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (taskDto.getPageIndex() == null) {
            //缺少参数 页数 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (taskDto.getPageSize() == null) {
            taskDto.setPageSize(200);
        }
        //组装查询条件
        Query query = new Query();
        //文件id相等
        Criteria criteria = Criteria.where("fileId").is(taskDto.getFileId());
        //项目id相等
        criteria.and("projectId").is(taskDto.getProjectId());
        query.addCriteria(criteria);
        //按顺序ID排序
        query.with(Sort.by(Sort.Direction.ASC, "sequenceId"));
        long count = mongoTemplate.count(query, ProjectTranslateFile.class);
        //mongo分页
        query.skip((taskDto.getPageIndex()-1)*taskDto.getPageSize());
        query.limit(taskDto.getPageSize());
        List<ProjectTranslateFile> ptfList = mongoTemplate.find(query, ProjectTranslateFile.class);
        PageDto pageDto = new PageDto();
        pageDto.setList(ptfList);
        pageDto.setTotal((int)count);
        return Result.getSuccessResult(pageDto);
    }

    @ApiOperation(value = "updateProjectTranslateList", notes = "任务模块-详情页-批量修改状态")
    @ApiResponses({@ApiResponse(code = 200, message = "Result", response = Result.class)})
    @PostMapping(value = "/updateProjectTranslateList")
    public Result updateProjectTranslateList(@RequestBody ProjectTranslateDto projectTranslateDto) {
        projectTranslateDto.setUserId(getUserId().toString());
        //TODO 先假设每一段内容的id集合前端可以提供
        //TODO 现不确定用户模块是否可以调用 先假设前端提供用户ID和用户昵称
        if(projectTranslateDto.getId() == null || projectTranslateDto.getId().size() == 0){
            //缺少参数 该文件每一条内容的id 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if(projectTranslateDto.getCurrentState() == null){
            //缺少参数 缺少也要修改的状态 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (!StringUtils.isNoneBlank(projectTranslateDto.getUserId())) {
            //缺少参数 用户id 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (!StringUtils.isNoneBlank(projectTranslateDto.getUserNikeName())) {
            //缺少参数 昵称 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        //循环 通过id修改状态
        projectTranslateDto.getId().forEach(id->{
            Query query = new Query(Criteria.where("_id").is(id));
            //修改内容对象构造器
            Update update = new Update();
            update.set("currentState",projectTranslateDto.getCurrentState());
            mongoTemplate.upsert(query, update, ProjectTranslateFile.class);
            //查询这条记录 TODO 用来保存修改历史
            taskService.addProjectTranslateFileHistory(mongoTemplate.findOne(query,ProjectTranslateFile.class),projectTranslateDto.getUserId(),projectTranslateDto.getUserNikeName(),"");

        });
        return Result.getSuccessResult();
    }

    @ApiOperation(value = "updateProjectTranslateRemarks", notes = "任务模块-详情页-修改备注")
    @ApiResponses({@ApiResponse(code = 200, message = "Result", response = Result.class)})
    @PostMapping(value = "/updateProjectTranslateRemarks")
    public Result updateProjectTranslateRemarks(@RequestBody UpdateRemarksDto updateRemarksDto) {
        updateRemarksDto.setUserId(getUserId().toString());
        //TODO 先假设每一段内容的id集合前端可以提供
        //TODO 现不确定用户模块是否可以调用 先假设前端提供用户ID和用户昵称
        if(updateRemarksDto.getId() == null){
            //缺少参数 一条内容的id 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (!StringUtils.isNoneBlank(updateRemarksDto.getRemarks())) {
            //缺少参数 备注 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (updateRemarksDto.getRemarks().length() > 100) {
            //缺少参数 备注过长 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (!StringUtils.isNoneBlank(updateRemarksDto.getUserId())) {
            //缺少参数 用户id 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        if (!StringUtils.isNoneBlank(updateRemarksDto.getUserNikeName())) {
            //缺少参数 昵称 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        Query query = new Query(Criteria.where("_id").is(updateRemarksDto.getId()));
        //修改内容对象构造器
        Update update = new Update();
        update.set("remarks",updateRemarksDto.getRemarks());
        mongoTemplate.upsert(query, update, ProjectTranslateFile.class);
        //查询这条记录 TODO 用来保存修改历史
        taskService.addProjectTranslateFileHistory(mongoTemplate.findOne(query,ProjectTranslateFile.class),updateRemarksDto.getUserId(),updateRemarksDto.getUserNikeName(),"");
        return Result.getSuccessResult();

    }

    @ApiOperation(value = "getProjectFileHistoryList", notes = "任务模块-修改历史记录")
    @ApiResponses({@ApiResponse(code = 200, message = "Result", response = Result.class)})
    @PostMapping(value = "/getProjectFileHistoryList")
    public Result getProjectFileHistoryList(@RequestBody ProjectTranslateFileHistoryDto projectTranslateFileHistoryDto) {
        //TODO 假设任务列表含有文件id
        if (projectTranslateFileHistoryDto.getTranslateId() == null) {
            //缺少参数 文件id 返回参数不正确
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
//        if (projectTranslateFileHistoryDto.getPageIndex() == null) {
//            //缺少参数 页数 返回参数不正确
//            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
//        }
//        if (projectTranslateFileHistoryDto.getPageSize() == null) {
//            //TODO 先默认20条
//            projectTranslateFileHistoryDto.setPageSize(20);
//        }
        //组装查询条件
        Query query = new Query();
        //文件id相等
        Criteria criteria = Criteria.where("translateId").is(projectTranslateFileHistoryDto.getTranslateId());
        query.addCriteria(criteria);
        //按修改时间排序
        query.with(Sort.by(Sort.Direction.DESC, "createDate"));
        //mongo分页
//        query.skip((projectTranslateFileHistoryDto.getPageIndex()-1)*projectTranslateFileHistoryDto.getPageSize());
//        query.limit(projectTranslateFileHistoryDto.getPageSize());
        List<ProjectTranslateFileHistory> ptfList = mongoTemplate.find(query, ProjectTranslateFileHistory.class);
        return Result.getSuccessResult(ptfList);

    }
}
