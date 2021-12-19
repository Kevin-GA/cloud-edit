package com.newtranx.cloud.edit.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "UpdateRemarksDto", description = "修改备注所用传输对象")
public class UpdateRemarksDto {

    //ProjectTranslateFile对象的Id
    private String id;

    //备注
    private String remarks;

    //userId
    private String userId;

    //用户昵称
    private String userNikeName;

}
