package com.newtranx.cloud.edit.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

/***
 * 用户角色关系对象
 * @author 佟文森
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("user_role")
@ApiModel(value = "userRole", description = "用户角色关系对象")
public class UserRole implements Serializable {

    private static final long serialVersionUID=1L;
    @TableId(type = IdType.AUTO)
    private BigInteger id;

    @ApiModelProperty(value = "角色id")
    @TableField("role_id")
    private BigInteger roleId;

    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private BigInteger userId;

    @ApiModelProperty(value = "")
    @TableField("authorities_id")
    private BigInteger authoritiesId;

}
