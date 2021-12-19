package com.newtranx.cloud.edit.mongodb;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;

@Data
@Document(collection="project_translate_file")
public class ProjectTranslateFile implements Serializable {

    private String id;

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

    //总数
    private Integer total;

    //已翻译数
    private Integer translatedCount;

    //此段落语句 当前状态 1=翻译  2=编辑  3=校对 4=完成
    private Integer currentState;

    //此段落语句 当前进度
    private String currentProgress;

    //译文类型 1=机翻
    private Integer translateType;

    //备注
    private String remarks;


}
