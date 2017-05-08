package com.shizuwei.service.constant;

public enum ErrorCode {
    SUCCESS(0, "success"),  
    NO_PERMISSION(100000, "no permission"),
    PARAM_ERROR(100001, "param error"),  
    PARAM_NULL(100002, "param null");
    
    private int code;
    private String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
