package com.newtranx.cloud.edit.dto;

import com.newtranx.cloud.edit.common.entities.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigInteger;

/***
 * 团队表对象
 * @author 佟文森
 */
@Data
@ApiModel(value = "TeamDto", description = "team传输对象")
public class TeamDto extends BaseQuery {

    @ApiModelProperty(value = "团队ID")
    private BigInteger id;

    @ApiModelProperty(value = "团队名称")
    private String teamName;

}
