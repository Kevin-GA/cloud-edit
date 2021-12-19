package com.newtranx.cloud.edit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="ProjectFileDto", description="项目文件传输对象")
public class ProjectFileDto {

    private BigInteger fileId;

    @ApiModelProperty(value = "项目ID")
    private BigInteger projectId;

    @ApiModelProperty(value = "用户ID")
    private BigInteger userId;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "文件扩展名")
    private String fileExt;

    @ApiModelProperty(value = "文件大小")
    private Integer fileSize;

    @ApiModelProperty(value = "中间文件")
    private String middleFile;

    @ApiModelProperty(value = "原文xlf文件")
    private String srcXlfPath;

    @ApiModelProperty(value = "译文xlf文件")
    private String tgtXlfPath;

    @ApiModelProperty(value = "目标文件")
    private String tgtPath;

    @ApiModelProperty(value = "总数")
    private Integer total;

    @ApiModelProperty(value = "已翻译数")
    private Integer translatedCount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标识 0：正常   1：已删除")
    private Integer isDel;

    @ApiModelProperty(value = "进度")
    private BigDecimal progress;

    @ApiModelProperty(value = "进度百分比")
    private String percentage;

}
