package com.vvss.FlavorFiesta.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.payloads.request.LoginRequest;
import com.vvss.FlavorFiesta.security.basicauth.BasicAuthToken;
import com.vvss.FlavorFiesta.services.UserService;
import com.vvss.FlavorFiesta.test_utils.TestController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class TestAuthController extends TestController {
    User testUser;
    String testUserPassword = "password";

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    void setUp() {
        // Test data
        testUser = new User("john", "john@example.com", testUserPassword);
        userService.saveUser(testUser);
    }

    @Test
    public void testLogin() throws Exception {
        LoginRequest request = new LoginRequest(testUser.getUsername(), testUserPassword);
        BasicAuthToken token = new BasicAuthToken(request.getUsername(), request.getPassword());
        String expectedCredentials = token.toAuthorizationHeader();

        String actualCredentials = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assert actualCredentials.equals(expectedCredentials);
    }
}
