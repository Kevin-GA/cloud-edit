package com.newtranx.cloud.edit.dto;

import com.newtranx.cloud.edit.common.entities.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@ApiModel(value = "UserTaskListDto", description = "任务传输对象")
public class UserTaskListDto extends BaseQuery {

    private BigInteger taskId;

    @ApiModelProperty(value = "项目id")
    private BigInteger projectId;

    @ApiModelProperty(value = "项目文件id")
    private BigInteger fileId;

    @ApiModelProperty(value = "用户id")
    private BigInteger userId;

    @ApiModelProperty(value = "工作类型")
    private String type;

    @ApiModelProperty(value = "交付时间")
    private Date dueTime;

    @ApiModelProperty(value = "状态 0：未开始  1：进行中  2：已完成")
    private Integer status;

    @ApiModelProperty(value = "进度")
    private BigDecimal progress;

    @ApiModelProperty(value = "已处理数")
    private BigInteger handledCount;

    @ApiModelProperty(value = "总数")
    private BigInteger tatalCount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标识 0：正常 1：已删除")
    private Integer isDel;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "文件地址")
    private String filePath;

    @ApiModelProperty(value = "文件名")
    private String fileName;

}
