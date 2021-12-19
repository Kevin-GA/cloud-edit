package com.newtranx.cloud.edit.dto;

import com.newtranx.cloud.edit.entities.ProjectProcess;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: niujiaxin
 * @Date: 2021-02-02 00:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectProcessVo extends ProjectProcess {

    private String typeName;
}
