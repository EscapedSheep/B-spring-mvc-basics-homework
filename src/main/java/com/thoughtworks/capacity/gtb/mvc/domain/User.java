package com.thoughtworks.capacity.gtb.mvc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.thoughtworks.capacity.gtb.mvc.exception.ErrorResponse.Message.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;

    @NotBlank(message = USER_NAME_CANNOT_EMPTY)
    @Size(min = 3, max = 10, message = USER_NAME_INVALID)
    @JsonProperty("username")
    private String name;

    @NotBlank(message = PASSWORD_CANNOT_EMPTY)
    @Size(min = 5, max = 12, message = PASSWORD_INVALID)
    private String password;

    @Email(message = EMAIL_INVALID)
    private String email;

}
