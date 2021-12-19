package com.newtranx.cloud.edit.dto;

import com.newtranx.cloud.edit.common.entities.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="UserQuery对象", description="")
public class UserQuery extends BaseQuery {

    @ApiModelProperty(value = "昵称或手机号")
    String mobileOrNickName;

    @ApiModelProperty(value = "用户名")
    String username;

    @ApiModelProperty(value = "用户id")
    String userId;

    @ApiModelProperty(value = "手机号")
    String mobile;

    @ApiModelProperty(value = "昵称")
    String nickName;

    @ApiModelProperty(value = "昵称")
    String teamId;

}
