package com.newtranx.cloud.edit.exception;

import com.newtranx.cloud.edit.common.entities.Result;
import com.newtranx.cloud.edit.common.enums.FailureCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {
    @ResponseBody
    @ExceptionHandler(GlobalException.class)
    public Result globalException(HttpServletResponse response, GlobalException ex) {
        log.info("ApiExceptionHandler...");
        log.info("错误代码：" + response.getStatus());
        return Result.getFailureResult(FailureCodeEnum.SERVICE_EXCEPTION.getCode(), FailureCodeEnum.SERVICE_EXCEPTION.getMsg(), ex.getMessage());
    }
}