package com.newtranx.cloud.edit.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/***
 * 任务对象
 * @author 佟文森
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("task")
@ApiModel(value = "Task", description = "任务对象")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private BigInteger taskId;

    @ApiModelProperty(value = "项目id")
    @TableField("project_id")
    private BigInteger projectId;

    @ApiModelProperty(value = "项目文件id")
    @TableField("file_id")
    private BigInteger fileId;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private BigInteger userId;

    @ApiModelProperty(value = "工作类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "交付时间")
    @TableField("due_time")
    private Date dueTime;

    @ApiModelProperty(value = "状态 0：未开始  1：进行中  2：已完成")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "进度")
    @TableField("progress")
    private BigDecimal progress;

    @ApiModelProperty(value = "已处理数")
    @TableField("handled_count")
    private BigInteger handledCount;

    @ApiModelProperty(value = "总数")
    @TableField("tatal_count")
    private BigInteger tatalCount;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "删除标识 0：正常 1：已删除")
    @TableField("is_del")
    private Integer isDel;


}
