package com.newtranx.cloud.edit.entities;

import com.baomidou.mybatisplus.annotation.*;
import com.newtranx.cloud.edit.enums.ProjectProcessTypesEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("project_process")
public class ProjectProcess implements Serializable
{
    @TableId(type = IdType.AUTO)
    private Long processId;
    @TableField("project_id")
    private Long projectId;

    @TableField("type")
    @EnumValue
    private ProjectProcessTypesEnum type;

    @TableField("status")
    private Integer status;

    @TableField("progress")
    private Double progress;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("is_del")
    private Integer isDel;

    @TableField(exist = false)
    private String handledCount;

    @TableField(exist = false)
    private String tatalCount;

}
