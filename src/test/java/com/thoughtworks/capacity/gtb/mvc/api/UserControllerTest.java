package com.thoughtworks.capacity.gtb.mvc.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capacity.gtb.mvc.domain.User;
import com.thoughtworks.capacity.gtb.mvc.repository.UserRepository;
import com.thoughtworks.capacity.gtb.mvc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        userRepository.setUserList(new ArrayList<User>());
    }

    @Test
    void should_register_user_given_name_and_password() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setName("userName");
        user.setPassword("password");
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isCreated());

        assertEquals(userRepository.getUserList().size(), 1);
        assertEquals(userRepository.getUserList().get(0).getName(),  user.getName());
        assertEquals(userRepository.getUserList().get(0).getPassword(), user.getPassword());
    }

    @Test
    void should_receive_error_given_invalid_name_or_password() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setName("");
        user.setPassword("password");
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())));

        user.setName("tom");
        user.setPassword("");
        userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())));
    }

    @Test
    void should_receive_error_when_name_existed() throws Exception{
        User user = new User();
        user.setName("userName");
        user.setPassword("password");
        userRepository.addUser(user);
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(HttpStatus.BAD_REQUEST.value())));
    }
}