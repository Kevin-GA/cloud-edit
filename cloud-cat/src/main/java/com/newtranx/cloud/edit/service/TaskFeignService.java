package com.newtranx.cloud.edit.service;

import com.newtranx.cloud.edit.common.entities.Result;
import com.newtranx.cloud.edit.dto.TaskFeignDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Component
@FeignClient(value = "task-service")
public interface TaskFeignService {

    @PostMapping(value = "/task/updateProgress", consumes = MediaType.APPLICATION_JSON_VALUE)
    Result updateProgress(@RequestBody TaskFeignDto taskFeignDto,@RequestHeader HttpHeaders httpHeaders);
}
