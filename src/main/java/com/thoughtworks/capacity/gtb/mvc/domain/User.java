package com.thoughtworks.capacity.gtb.mvc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {
    private Integer id;

    @NotNull(message = "用户名不为空")
    @Size(min = 3, max = 10, message = "用户名不合法")
    @JsonProperty("username")
    private String name;

    @NotNull(message = "密码是不为空")
    @Size(min = 5, max = 12, message = "密码不合法")
    private String password;

    @Email(message = "邮箱地址不合法")
    private String email;

    public User(@NotNull(message = "用户名不为空") @Size(min = 3, max = 10, message = "用户名不合法") String name, @NotNull(message = "密码是不为空") @Size(min = 5, max = 12, message = "密码不合法") String password, @Email(message = "邮箱地址不合法") String email) {
        this.id = null;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
