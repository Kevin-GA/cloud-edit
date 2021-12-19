package com.newtranx.cloud.edit.dto;

import com.newtranx.cloud.edit.entities.ProjectFile;
import com.newtranx.cloud.edit.entities.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: niujiaxin
 * @Date: 2021-02-05 09:33
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectFileVo {

    private ProjectFile projectFile;

    private List<Task> taskList;
}
