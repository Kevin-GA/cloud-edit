package com.newtranx.task.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamUser {
    @ApiModelProperty(value = "团队ID")
    private String teamId;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "工作性质 0：全职  1：兼职")
    private Integer jobType;

    @ApiModelProperty(value = "角色编号")
    private String roleId;
}
