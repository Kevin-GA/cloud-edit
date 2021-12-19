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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("project_process")
@ApiModel(value = "ProjectProcess", description = "状态对象")
public class ProjectProcess {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private BigInteger processId;

    @ApiModelProperty(value = "项目ID")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty(value = "类型    1：翻译  2：编辑  3：校验")
    @TableField("type")
    private Integer Integer;

    @ApiModelProperty(value = "状态 0：未开始 1：进行中  2：已完成")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "进度")
    @TableField("progress")
    private BigDecimal progress;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "删除标识 0：正常   1：已删除")
    @TableField("is_del")
    private Integer isDel;

}
