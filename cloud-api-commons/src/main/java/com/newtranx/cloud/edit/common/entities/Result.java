package com.newtranx.cloud.edit.common.entities;

import com.newtranx.cloud.edit.common.enums.FailureCodeEnum;

import java.io.Serializable;

/**
 * 描述:通用返回实体
 *
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功与否
     */
    private boolean isSuccess;
    /**
     * 结果代码
     */
    private String resultCode;
    /**
     * 结果信息
     */
    private String resultMessage;

    private Result() {
    }

    /**
     * 数据载体
     */
    private T data;

    /**
     * 获取成功返回结果
     *
     * @param data
     * @return
     */
    public static <T> Result getSuccessResult(T data) {
        return getResult(true, null, null, data);
    }

    public static <T> Result getSuccessResult(String resultCode, String resultMessage, T data) {
        return getResult(true, resultCode, resultMessage, data);
    }

    public static Result getSuccessResult(String resultCode, String resultMessage) {
        return getResult(true, resultCode, resultMessage, null);
    }

    /**
     * 获取成功返回结果
     *
     * @return
     */
    public static Result getSuccessResult() {
        return getResult(true, null, null, null);
    }

    /**
     * 描述:获取失败返回结果
     *
     * @param failureCode 错误码
     * @return
     */
    public static Result getFailureResult(String failureCode) {
        return getResult(false, failureCode, null, null);
    }

    /**
     * 描述:获取失败返回结果
     *
     * @param failureCode    错误码
     * @param failureMessage 错误信息
     * @return
     */
    public static Result getFailureResult(String failureCode, String failureMessage) {
        return getResult(false, failureCode, failureMessage, null);
    }

    public static Result getFailureResult(FailureCodeEnum failureCodeEnum) {
        return getResult(false, failureCodeEnum.getCode(), failureCodeEnum.getMsg(), null);
    }

    /**
     * 描述:获取失败返回结果
     *
     * @param failureCode    错误码
     * @param failureMessage 错误信息
     * @param data           数据信息（可以保留失败时的交易快照）
     * @return
     */
    public static <T> Result getFailureResult(String failureCode, String failureMessage, T data) {
        return getResult(false, failureCode, failureMessage, data);
    }

    private static <T> Result getResult(boolean isSuccess, String resultCode, String resultMessage, T data) {
        Result result = new Result();
        result.setSuccess(isSuccess);
        result.setResultCode(resultCode);
        result.setResultMessage(resultMessage);
        result.setData(data);
        return result;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    /**
     * 获取数据载体
     *
     * @return data 数据载体
     */
    public T getData() {
        return data;
    }

    /**
     * 设置数据载体
     *
     * @param data 数据载体
     */
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "isSuccess=" + isSuccess +
                ", resultCode='" + resultCode + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                ", data=" + data +
                '}';
    }
}
