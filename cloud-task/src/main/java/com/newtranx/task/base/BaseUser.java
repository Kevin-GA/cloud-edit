package com.newtranx.task.base;

import lombok.Data;

@Data
public class BaseUser {
    private Long userId;
    private String username;
    private boolean isAdmin;
}
