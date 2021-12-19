package com.newtranx.cloud.edit.dto;

import com.newtranx.cloud.edit.common.entities.BaseQuery;
import lombok.Data;

@Data
public class MenuQuery extends BaseQuery {
    private Long roleId;

    private String keyword;

    private Long parentId;

}
