package com.newtranx.cloud.edit.common.enums;


/**
 * 描述:返回异常映射枚举类
 * <p>创建人：jrzhangwei 创建日期：2014年7月16日 </p>
 *
 * @version V1.0
 */
public enum FailureCodeEnum {

    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常"),
    INVALID_PARAM("INVALID_PARAM", "参数不正确"),
    SERVICE_EXCEPTION("SERVICE_EXCEPTION", "服务异常"),
    PARAM_NULL("PARAM_NULL", "参数为空"),
    NETWORK_EXCEPITON("NETWORK_EXCEPITON", "网络异常"),
    SEND_SMS_CODE_ERROR("SEND_SMS_CODE_ERROR", "系统异常"),
    ;

    FailureCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String msg;

    private String code;

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }
}
