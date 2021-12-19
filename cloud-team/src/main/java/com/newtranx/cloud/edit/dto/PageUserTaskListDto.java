package com.newtranx.cloud.edit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/***
 * 团队表对象
 * @author 佟文森
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PageUserTaskListDto implements Serializable {

    private Integer total;

    List<UserTaskListDto> userTaskList;

}
