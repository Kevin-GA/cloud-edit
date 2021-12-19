package com.newtranx.cloud.edit.entities;

import lombok.Data;

@Data
public class JobTypeRole {
    private Integer jobType;
    private Long roleId;
    private String roleCode;
    private String roleName;
}
