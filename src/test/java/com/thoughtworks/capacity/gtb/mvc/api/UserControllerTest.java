package com.thoughtworks.capacity.gtb.mvc.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.capacity.gtb.mvc.domain.User;
import com.thoughtworks.capacity.gtb.mvc.repository.UserRepository;
import com.thoughtworks.capacity.gtb.mvc.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

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
}