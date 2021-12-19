package com.newtranx.cloud.edit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: niujiaxin
 * @Date: 2021-02-02 00:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectParamVo {

    private String teamId;

    private String userId;

    private Integer pageNum;

    private Integer pageSize;
    //        name     progress  dueTime

    private Boolean orderByName;

    private Boolean orderByProgress;

    private Boolean orderByDueTime;

    private Boolean isAsc;


}
