package com.thoughtworks.capacity.gtb.mvc.exception;

public class UserNameOrPasswordNotValidException extends RuntimeException{
    public UserNameOrPasswordNotValidException() {
        super("用户名或密码错误");
    }
}
