package com.newtranx.cloud.edit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: niujiaxin
 * @Date: 2021-03-18 20:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDownParams {
    List<FileDownParam> fullpaths;
}
