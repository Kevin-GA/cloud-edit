package com.newtranx.cloud.edit.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("project_file")
public class ProjectFile implements Serializable
{
    @TableId(type = IdType.AUTO)
    private Long fileId;

    @TableField("project_id")
    private String projectId;

    @TableField("user_id")
    private String userId;

    @TableField("file_name")
    private String fileName;

    @TableField("file_path")
    private String filePath;

    @TableField("file_ext")
    private String fileExt;

    @TableField("file_size")
    private Integer fileSize;

    @TableField("middle_file")
    private String middleFile;

    @ApiModelProperty(value = "原文xlf文件")
    @TableField("src_xlf_path")
    private String srcXlfPath;

    @ApiModelProperty(value = "译文xlf文件")
    @TableField("tgt_xlf_path")
    private String tgtXlfPath;

    @TableField("proofread_path")
    private String proofreadPath;

    @TableField("edit_path")
    private String editPath;

    @ApiModelProperty(value = "译后终稿")
    @TableField("ft_path")
    private String ftPath;

    @ApiModelProperty(value = "双语终稿")
    @TableField("bilingual_path")
    private String bilingualPath;

    @TableField("tgt_path")
    private String tgtPath;

    @TableField("total")
    private Integer total;

    @TableField("translated_count")
    private Integer translatedCount;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("is_del")
    private Integer isDel;
//    初始值-1
//    split接口：  0-成功 ； 1-失败；
//    doc/eng接口： 10-成功；11-失败
//    doc/chieng接口： 20-成功；21-失败；
    @TableField("status")
    private Integer status;


}
