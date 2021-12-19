package com.newtranx.cloud.edit.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("task")
@ApiModel(value="任务对象", description="")
public class Task implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private String taskId;

    @TableField("project_id")
    private Long projectId;

    @TableField("file_id")
    private Long fileId;

    @TableField("user_id")
    private Long userId;

    @TableField(exist = false)
    private String userName;

    @TableField("type")
    private String type;

    @TableField("due_time")
    private Date dueTime;

    @TableField("status")
    private Integer status;

    @TableField("progress")
    private Double progress;

    @TableField("handled_count")
    private Long handledCount;

    @TableField("tatal_count")
    private Long tatalCount;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("is_del")
    private Integer isDel;


}
