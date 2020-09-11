package com.thoughtworks.capacity.gtb.mvc.api;

import com.thoughtworks.capacity.gtb.mvc.domain.User;
import com.thoughtworks.capacity.gtb.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import static com.thoughtworks.capacity.gtb.mvc.exception.ErrorResponse.Message.PASSWORD_INVALID;
import static com.thoughtworks.capacity.gtb.mvc.exception.ErrorResponse.Message.USER_NAME_INVALID;

@RestController
@Validated
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid User user) {
        userService.registerUser(user);
    }

    @GetMapping("/login")
    public User login(@RequestParam("username")
                      @Size(min = 3, max = 10, message = USER_NAME_INVALID)
                                  String name,
                      @RequestParam
                      @Size(min = 5, max = 12, message = PASSWORD_INVALID)
                              String password) {
        return userService.login(name, password);
    }
}
