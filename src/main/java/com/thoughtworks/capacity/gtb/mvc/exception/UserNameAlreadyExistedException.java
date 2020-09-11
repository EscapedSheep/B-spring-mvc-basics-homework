package com.thoughtworks.capacity.gtb.mvc.exception;

import static com.thoughtworks.capacity.gtb.mvc.exception.ErrorResponse.Message.USER_NAME_EXISTED;

public class UserNameAlreadyExistedException extends RuntimeException{
    public UserNameAlreadyExistedException() {
        super(USER_NAME_EXISTED);
    }
}
