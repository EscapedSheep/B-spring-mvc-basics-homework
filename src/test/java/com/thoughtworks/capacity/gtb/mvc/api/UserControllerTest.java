package com.thoughtworks.capacity.gtb.mvc.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capacity.gtb.mvc.domain.User;
import com.thoughtworks.capacity.gtb.mvc.repository.UserRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void should_login_given_correct_name_and_password() throws Exception{
        User user = new User();
        user.setName("userName");
        user.setPassword("password");
        user.setEmail("a@b.com");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        user = userRepository.addUser(user);

        mockMvc.perform(get("/login").param("username", user.getName()).param("password",  user.getPassword()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(user.getName())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.id", is(user.getId())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    void should_receive_error_when_login_given_name_or_password_invalid() throws Exception{
        mockMvc.perform(get("/login").param("username", "").param("password","12345"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/login").param("username", "tom").param("password","1234"))
                .andExpect(status().isBadRequest());
    }
}