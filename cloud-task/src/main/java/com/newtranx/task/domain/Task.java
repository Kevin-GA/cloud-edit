package com.newtranx.task.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author AHui
 * @since 2021-01-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Task对象", description = "")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "task_id", type = IdType.ID_WORKER)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long taskId;

    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @ApiModelProperty(value = "项目文件id")
    private Long  fileId;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "用户id")
    @TableField(exist = false)
    private String userIds;
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
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "删除标识 0：正常  1：已删除")
    @TableLogic
    private Boolean isDel;

    @ApiModelProperty(value = "项目文件id,多文件的时候用英文;连接组成字符串")
    @TableField(exist = false)
    private String  fileIds;

    @ApiModelProperty(value = "工作类型    1：翻译  2：编辑  3：校验，多文件，多类型的时候用英文;连接组成字符串")
    @TableField(exist = false)
    private String types;

    @ApiModelProperty(value = "团队名称（公司名称）")
    @TableField(exist = false)
    private String teamName;

    @ApiModelProperty(value = "项目名称")
    @TableField(exist = false)
    private String name;

    @ApiModelProperty(value = "团队id")
    @TableField(exist = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long teamId;

    @ApiModelProperty(value = "源语言")
    @TableField(exist = false)
    private String srcLang;

    @ApiModelProperty(value = "目标语言")
    @TableField(exist = false)
    private String tgtLang;

    @ApiModelProperty(value = "排序字段：sortColumns=1：按公司名称排序；sortColumns=2：按任务排序；sortColumns=3：按交付时间排序")
    @TableField(exist = false)
    private String sortColumns;

    @ApiModelProperty(value = "0顺序，1倒序")
    @TableField(exist = false)
    private String sortType;

}