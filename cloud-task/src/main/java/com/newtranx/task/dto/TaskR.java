package com.newtranx.task.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.newtranx.task.domain.Task;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "TaskR对象", description = "任务展示实体类")
public class TaskR{

    private static final long serialVersionUID = -7038822806007846490L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long taskId;

    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @ApiModelProperty(value = "项目文件id")
    private Long  fileId;
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "工作类型  1：翻译  2：编辑  3：校验")
    private String type;

    @ApiModelProperty(value = "交付时间")
    private Date dueTime;

    @ApiModelProperty(value = "状态 0：未开始  1：进行中  2：已完成")
    private Integer status;

    @ApiModelProperty(value = "进度")
    private BigDecimal progress;

    @ApiModelProperty(value = "已处理数")
    private Long handledCount;

    @ApiModelProperty(value = "总数")
    private Long tatalCount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标识 0：正常  1：已删除")
    private Boolean isDel;

    @ApiModelProperty(value = "项目文件id,多文件的时候用英文;连接组成字符串")
    private String  fileIds;

    @ApiModelProperty(value = "工作类型    1：翻译  2：编辑  3：校验，多文件，多类型的时候用英文;连接组成字符串")
    private String types;

    @ApiModelProperty(value = "团队名称（公司名称）")
    private String teamName;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "项目名称")

    private String name;

    @ApiModelProperty(value = "团队id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long teamId;

    @ApiModelProperty(value = "源语言")

    private String srcLang;

    @ApiModelProperty(value = "目标语言")

    private String tgtLang;

    @ApiModelProperty(value = "排序字段：sortColumns=1：按公司名称排序；sortColumns=2：按任务排序；sortColumns=3：按交付时间排序")
    private String sortColumns;

    @ApiModelProperty(value = "0顺序，1倒序")
    private String sortType;


}
