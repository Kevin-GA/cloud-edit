package com.newtranx.task.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "role-service")
public interface RoleService {

}
