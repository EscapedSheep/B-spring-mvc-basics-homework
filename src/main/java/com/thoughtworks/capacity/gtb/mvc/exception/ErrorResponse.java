package com.thoughtworks.capacity.gtb.mvc.exception;

public class ErrorResponse {
    private int code;
    private String message;

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class Message {
        public static final String USER_NAME_INVALID = "用户名不合法";
        public static final String USER_NAME_CANNOT_EMPTY = "用户名不为空";
        public static final String PASSWORD_CANNOT_EMPTY = "密码是不为空";
        public static final String PASSWORD_INVALID = "密码不合法";
        public static final String EMAIL_INVALID = "邮箱地址不合法";
        public static final String USER_NAME_EXISTED = "用户已存在";
        public static final String LOGIN_FAIL = "用户名或密码错误";
    }
}
