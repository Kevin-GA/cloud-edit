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
 * 团队表对象
 * @author 佟文森
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("project")
@ApiModel(value = "project", description = "project对象")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private BigInteger projectId;

    @ApiModelProperty(value = "项目名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "源语言")
    @TableField("src_lang")
    private String srcLang;

    @ApiModelProperty(value = "目标语言")
    @TableField("tgt_lang")
    private String tgtLang;

    @ApiModelProperty(value = "领域")
    @TableField("field")
    private String field;

    @ApiModelProperty(value = "交付时间")
    @TableField("due_time")
    private Date dueTime;

    @ApiModelProperty(value = "进度")
    @TableField("progress")
    private BigDecimal progress;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "团队id")
    @TableField("team_id")
    private BigInteger teamId;

    @ApiModelProperty(value = "")
    @TableField("create_by")
    private String createBy;

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
