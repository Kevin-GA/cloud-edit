package com.newtranx.cloud.edit.dto;

import com.newtranx.cloud.edit.common.entities.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "ProjectTranslateDto", description = "编辑，详情，内容，传输对象")
public class ProjectTranslateDto extends BaseQuery {

    //每一段内容的id集合
    private List<String> id;

    //批量修改原文译文
    private List<TextListDto> textList;

    //一条原文译文记录的id
    private String textId;

    //userId
    private String userId;

    //用户昵称
    private String userNikeName;

    //项目id
    private String projectId;

    //文件id
    private String fileId;

    //字段 语句 序号 排序序号 第几句话
    private String sequenceId;

    //原文内容
    private String originalText;

    //译文内容
    private String translateText;

    //此段落语句 当前状态 1=翻译  2=编辑  3=校对 4=完成
    private Integer currentState;

    //此段落语句 当前进度
    private String currentProgress;

    //译文类型 1=机翻
    private Integer translateType;

    //备注
    private String remarks;

}
