package com.newtranx.cloud.edit.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("project")
public class Project implements Serializable
{
    @TableId(type = IdType.AUTO)
    private Long projectId;

    @TableField("name")
    private String name;

    @TableField("src_lang")
    private String srcLang;

    @TableField("tgt_lang")
    private String tgtLang;

    @TableField("field")
    private String field;

    @TableField("due_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date dueTime;

    @TableField("progress")
    private Double progress;

    @TableField("remark")
    private String remark;

    @TableField("team_id")
    private String teamId;

    @TableField("create_by")
    private String createBy;

    @TableField(exist = false)
    private String createByName;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("is_del")
    private Integer isDel;

    @TableField(exist = false)
    private String process;

    @TableField(exist = false)
    private List<ProjectProcess> projectProgress;

    @TableField(exist = false)
    private List<String> users;


}
