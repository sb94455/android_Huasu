package com.huasu.equipment.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 基础响应类
 * @param <T> 数据类型
 */
public class BaseResponse<T> implements Serializable {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private T data;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    @SerializedName("total")
    private int total;

    // Getters
    public boolean isSuccess() { return success; }
    public T getData() { return data; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public int getTotal() { return total; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setData(T data) { this.data = data; }
    public void setError(String error) { this.error = error; }
    public void setMessage(String message) { this.message = message; }
    public void setTotal(int total) { this.total = total; }
}
