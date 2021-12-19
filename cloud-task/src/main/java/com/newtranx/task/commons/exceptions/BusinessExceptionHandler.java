package com.newtranx.task.commons.exceptions;


import com.newtranx.task.commons.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局业务异常处理
 *
 * @author Ahui
 * @since v1.0.0
 */
@Slf4j
@ControllerAdvice
public class BusinessExceptionHandler {
    @ExceptionHandler({BusinessException.class, Exception.class})
    public ResponseEntity<?> handlerException(HttpServletRequest request, Exception ex) {
        Map<String, Object> error = new HashMap<>(2);

        // 业务异常
        if (ex instanceof BusinessException) {
            error.put("resultCode", ((BusinessException) ex).getCode());
            error.put("resultMessage", ex.getMessage());
            log.warn("[全局业务异常]\r\n业务编码：{}\r\n异常记录：{}", error.get("resultCode"), error.get("resultMessage"));
        }
        // 统一处理 JSON 参数验证
        else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
            String msg = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .distinct()
                    .collect(Collectors.joining(","));
            error.put("resultCode", HttpStatus.BAD_REQUEST.value());
            error.put("resultMessage", msg);
        }

        // 统一处理表单绑定验证
        else if (ex instanceof BindException) {
            BindException bindException = (BindException) ex;
            BindingResult bindingResult = bindException.getBindingResult();
            String msg = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .distinct()
                    .collect(Collectors.joining(","));
            error.put("resultCode", HttpStatus.UNAUTHORIZED.value());
            error.put("resultMessage", msg);
        }
        else if (ex instanceof AccessDeniedException) {
            log.warn("[授权认证异常]\r\n业务编码：{}\r\n异常记录：{}", HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
            error.put("resultCode", HttpStatus.UNAUTHORIZED.value());
            error.put("message", ex.getMessage());
        }
        // 未知错误
        else {
            error.put("resultCode", ResponseCode.UNKNOWN.code());
            error.put("resultMessage", ResponseCode.UNKNOWN.message());
            log.error(ex.getMessage());
        }

        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
