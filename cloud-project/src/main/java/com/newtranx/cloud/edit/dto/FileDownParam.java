package com.newtranx.cloud.edit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: niujiaxin
 * @Date: 2021-03-15 18:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDownParam {

    Long fileId;
//    type:1,译文；2，双语
    Integer type;
    String fullpath;
}
