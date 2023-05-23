package com.bindada.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {
    private long code;
    private String message;
    private T data;

    public ApiResponse(int code) {
        this.code = code;
    }

    /**
     * 请求成功
     */
    public static ApiResponse success(String message) {
        return new ApiResponse(200, message, null);
    }

    /**
     * 请求成功
     */
    public static ApiResponse<Object> success(java.util.List<String> data) {
        return new ApiResponse<>(200, "成功", data);
    }

    /**
     * 请求成功
     */
    public static ApiResponse<Object> success(String message, Object data) {
        return new ApiResponse<>(200, message, data);
    }

    /**
     * 请求错误
     */
    public static ApiResponse error(String message) {
        return new ApiResponse(500, message, null);
    }

    /**
     * 请求失败
     */
    public static ApiResponse error(long code, String message) {
        return new ApiResponse(code, message, null);
    }

    /**
     * 请求失败
     */
    public static ApiResponse fail(String message) {
        return new ApiResponse(1000, message, null);
    }

    /**
     * 请求失败
     */
    public static ApiResponse fail(int code) {
        return new ApiResponse(code);
    }

    /**
     * 请求失败
     */
    public static ApiResponse fail(int code, String message) {
        return new ApiResponse(code, message, null);
    }

    /**
     * 请求失败
     */
    public static ApiResponse<Exception> fail(Exception data) {
        return new ApiResponse<>(1000, "失败", data);
    }
}
