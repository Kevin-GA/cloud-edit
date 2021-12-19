package com.newtranx.cloud.edit.dto;

import com.newtranx.cloud.edit.common.entities.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/***
 * 团队表对象
 * @author 佟文森
 */
@Data
@ApiModel(value = "TeamUserDto", description = "teamUser增加团队成员传输对象")
public class TeamUserDto extends BaseQuery {

    @ApiModelProperty(value = "团队ID")
    private String teamId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "工作性质 0：全职  1：兼职")
    private Integer jobType;

    @ApiModelProperty(value = "角色编号")
    private String roleId;


}
