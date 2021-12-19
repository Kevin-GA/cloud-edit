package com.newtranx.task.commons.exceptions;


import com.newtranx.task.commons.response.ResponseCode;

/**
 * 全局业务异常
 *
 * @author Ahui
 * @since v1.0.0
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 4868842805385777275L;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BusinessException() {}

    public BusinessException(String message) {
        super(message);
        this.code = "-1";
    }

    public BusinessException(ResponseCode status) {
        super(status.message());
        this.code = status.code().toString();
    }
}
