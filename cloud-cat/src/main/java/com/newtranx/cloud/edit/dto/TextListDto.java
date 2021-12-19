package com.newtranx.cloud.edit.dto;

import com.newtranx.cloud.edit.common.entities.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "TextListDto", description = "批量修改原文译文")
public class TextListDto extends BaseQuery {

    //id
    private String textId;

    //原文内容
    private String originalText;

    //译文内容
    private String translateText;


}
