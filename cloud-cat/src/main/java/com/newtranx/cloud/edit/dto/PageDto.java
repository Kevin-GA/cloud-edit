package com.newtranx.cloud.edit.dto;

import com.newtranx.cloud.edit.common.entities.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "TextListDto", description = "批量修改原文译文")
public class PageDto extends BaseQuery {

    private Integer total;

    private List<?> list;


}
