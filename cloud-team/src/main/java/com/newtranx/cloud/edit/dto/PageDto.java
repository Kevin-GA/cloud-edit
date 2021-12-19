package com.newtranx.cloud.edit.dto;

import com.newtranx.cloud.edit.entities.TeamUserPlus;
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
public class PageDto implements Serializable {

    private Integer total;

    List<?> teamUserList;

}
