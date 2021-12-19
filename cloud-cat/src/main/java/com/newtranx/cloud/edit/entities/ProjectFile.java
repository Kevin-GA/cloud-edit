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
@TableName("project_file")
@ApiModel(value = "ProjectFile", description = "项目文件对象")
public class ProjectFile {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private BigInteger fileId;

    @ApiModelProperty(value = "项目ID")
    @TableField("project_id")
    private BigInteger projectId;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private BigInteger userId;

    @ApiModelProperty(value = "文件名")
    @TableField("file_name")
    private String fileName;

    @ApiModelProperty(value = "文件路径")
    @TableField("file_path")
    private String filePath;

    @ApiModelProperty(value = "文件扩展名")
    @TableField("file_ext")
    private String fileExt;

    @ApiModelProperty(value = "文件大小")
    @TableField("file_size")
    private Integer fileSize;

    @ApiModelProperty(value = "中间文件")
    @TableField("middle_file")
    private String middleFile;

    @ApiModelProperty(value = "原文xlf文件")
    @TableField("src_xlf_path")
    private String srcXlfPath;

    @ApiModelProperty(value = "译文xlf文件")
    @TableField("tgt_xlf_path")
    private String tgtXlfPath;

    @ApiModelProperty(value = "目标文件")
    @TableField("tgt_path")
    private String tgtPath;

    @ApiModelProperty(value = "总数")
    @TableField("total")
    private Integer total;

    @ApiModelProperty(value = "已翻译数")
    @TableField("translated_count")
    private Integer translatedCount;

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
