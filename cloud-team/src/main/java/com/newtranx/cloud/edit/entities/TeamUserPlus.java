package com.newtranx.cloud.edit.entities;

import lombok.Data;

import java.util.List;

@Data
public class TeamUserPlus {
    private String teamId;
    private String userId;
    private String mobile;
    private String nickName;
    private List<JobTypeRole> jobTypeRoles;
}
