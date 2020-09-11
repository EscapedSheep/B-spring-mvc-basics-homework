package com.thoughtworks.capacity.gtb.mvc.exception;

import static com.thoughtworks.capacity.gtb.mvc.exception.ErrorResponse.Message.LOGIN_FAIL;

public class UserNameOrPasswordNotValidException extends RuntimeException{
    public UserNameOrPasswordNotValidException() {
        super(LOGIN_FAIL);
    }
}
