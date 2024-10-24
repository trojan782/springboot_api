package com.example.demo.controller;

import com.example.demo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // to convert Java objects to json

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON));
    }

    @Test
    public void testGetUserById() throws Exception {
        String userId = "1";
        mockMvc.perform(get("/api/users/{id}",  userId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId));
    }

    @Test
    public void testCreateUser() throws Exception {
        User newUser = new User("1", "John Doe",  "john@example.com", 30);
        String userJson = objectMapper.writeValueAsString(newUser);

        mockMvc.perform(
                post("/api/users")
                        .contentType(APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    public void testUpdateUser() throws Exception {
//        TODO: complete this test
    }

    @Test
    public void testDeleteUser() throws Exception {
//        TODO: complete this test
    }
}