package com.newtranx.task.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author AHui
 * @since 2021-01-27
 */
@Data
@ApiModel(value = "TeamUserR对象", description = "项目团队展示列表实体类")
public class TeamUserR  implements Serializable {
    private static final long serialVersionUID = 1621557007058228457L;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "角色（管理员， 项目经理， 语料管理员, 译员）")
    private String roleName;
}
