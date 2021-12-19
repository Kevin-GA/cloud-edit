package com.newtranx.cloud.edit.dto;

import com.newtranx.cloud.edit.common.entities.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigInteger;

@Data
@ApiModel(value = "TaskDto", description = "任务传输对象")
public class TaskDto extends BaseQuery {

    @ApiModelProperty(value = "项目id")
    private BigInteger projectId;

    @ApiModelProperty(value = "项目文件id")
    private BigInteger fileId;

    @ApiModelProperty(value = "用户id")
    private BigInteger userId;

    @ApiModelProperty(value = "工作类型")
    private String type;

    @ApiModelProperty(value = "状态 0：未开始  1：进行中  2：已完成")
    private Integer status;

    @ApiModelProperty(value = "删除标识 0：正常 1：已删除")
    private Integer isDel;

    private String userIds;

}
