package com.newtranx.cloud.edit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newtranx.cloud.edit.base.BaseController;
import com.newtranx.cloud.edit.common.entities.Result;
import com.newtranx.cloud.edit.common.enums.FailureCodeEnum;
import com.newtranx.cloud.edit.dto.ProjectFileDto;
import com.newtranx.cloud.edit.entities.ProjectFile;
import com.newtranx.cloud.edit.service.ProjectFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.NumberFormat;

/***
 * 项目文件相关接口
 * @author 佟文森
 */
@Api(value = "任务接口")
@RestController
@Slf4j
public class ProjectFileController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    ProjectFileService projectFileService;

    @ApiOperation(value = "getFileProgress", notes = "任务模块-进度及字数查询")
    @ApiResponses({@ApiResponse(code = 200, message = "Result", response = Result.class)})
    @PostMapping(value = "/getFileProgress")
    public Result getFileProgress(@RequestBody ProjectFileDto projectFileDto) {
        //TODO 先假设当前端能提供当前FileID
        if (projectFileDto.getFileId() == null) {
            //缺少参数 文件ID 返回参数不正确
            LOG.info("[ProjectFileController.getFileProgress] 缺少参数 文件ID 返回参数不正确");
            return Result.getFailureResult(FailureCodeEnum.INVALID_PARAM);
        }
        //创建查询构造器 用来组装查询条件
        QueryWrapper<ProjectFile> queryWrapper = new QueryWrapper();
        //组装teamId等于前端传来的teamId
        queryWrapper.lambda().eq(ProjectFile::getFileId, projectFileDto.getFileId());
        //调用通用service封装的查询统计
        ProjectFile projectFile = projectFileService.getOne(queryWrapper);
        if(projectFile != null){
            //复制给传输对象
            ProjectFileDto pfDto = new ProjectFileDto();
            BeanUtils.copyProperties(projectFile,pfDto);
            if(pfDto.getTotal() < 1){
                pfDto.setProgress(new BigDecimal(0));
                pfDto.setPercentage("0%");
                return Result.getSuccessResult("500","总字数为0",0);
            }
            //计算精确进度
            BigDecimal total = new BigDecimal(pfDto.getTotal());
            BigDecimal translatedCount = new BigDecimal(pfDto.getTranslatedCount());
            //保留两位小数
            BigDecimal progress = translatedCount.divide(total).setScale(2);
            pfDto.setProgress(progress);
            //格式化成百分比
            NumberFormat percent = NumberFormat.getPercentInstance();
            percent.setMaximumFractionDigits(2);
            pfDto.setPercentage(percent.format(progress));
            //返回统计结果
            return Result.getSuccessResult(pfDto);
        }
        return Result.getFailureResult("500","项目文件不存在");
    }


}
