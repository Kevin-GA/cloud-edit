package com.newtranx.cloud.edit.dto;

import com.newtranx.cloud.edit.common.entities.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigInteger;
/***
 * 用户角色关系传输对象
 * @author 佟文森
 */
@Data
@ApiModel(value = "UserRoleDto", description = "用户角色关系传输对象")
public class UserRoleDto extends BaseQuery {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "用户id")
    private BigInteger userId;

    @ApiModelProperty(value = "用户id")
    private BigInteger teamId;

    @ApiModelProperty(value = "")
    private BigInteger authoritiesId;

}
