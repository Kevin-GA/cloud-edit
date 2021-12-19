package com.newtranx.cloud.edit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="CodeDto对象", description="")
public class CodeDto {

    @ApiModelProperty(value = "code值")
    private String code;

    @ApiModelProperty(value = "code路径")
    private String codePath;

}
