package com.newtranx.cloud.edit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value="MenuDto对象", description="")
public class MenuDto implements Serializable{

        private static final long serialVersionUID=1L;
        @ApiModelProperty(value = "菜单/按钮ID")
        private Long menuId;

        @ApiModelProperty(value = "上级菜单ID")
        private Long parentId;

        @ApiModelProperty(value = "菜单/按钮名称")
        private String menuName;

        @ApiModelProperty(value = "对应路由path")
        private String path;

        @ApiModelProperty(value = "对应路由组件component")
        private String component;

        @ApiModelProperty(value = "权限标识")
        private String perms;

        @ApiModelProperty(value = "图标")
        private String icon;

        @ApiModelProperty(value = "类型 0菜单 1按钮")
        private String type;

        @ApiModelProperty(value = "排序")
        private Double orderNum;

        @ApiModelProperty(value = "创建时间")
        private Date createTime;



}
