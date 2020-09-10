package com.thoughtworks.capacity.gtb.mvc.exception;

public class UserNameAlreadyExistedException extends RuntimeException{
    public UserNameAlreadyExistedException() {
        super("用户已存在");
    }
}
