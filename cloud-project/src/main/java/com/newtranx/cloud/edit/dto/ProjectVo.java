package com.newtranx.cloud.edit.dto;

import com.newtranx.cloud.edit.entities.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: niujiaxin
 * @Date: 2021-02-02 00:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectVo {

    private Project project;

    private List<ProjectFileVo> projectFiles;

}
