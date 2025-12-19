package com.vvss.FlavorFiesta.example_for_homework;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.security.basicauth.BasicAuthToken;
import com.vvss.FlavorFiesta.services.UserService;
import com.vvss.FlavorFiesta.test_utils.TestControllerIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.vvss.FlavorFiesta.payloads.request.LoginRequest;
import com.vvss.FlavorFiesta.payloads.request.SignupRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IncrementalIntegrationModuleATest extends TestControllerIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String testUserPassword = "password";
    private User testUser;
    private User otherUser;

    private String testUser3Username = "testUser3";
    private String testUser3Email = "test3@example.com";
    private String testUser3Password = "anotherPassword";



    @BeforeAll
    void setUp() {
        // Create test users
        testUser = new User("testUser1", "test1@example.com", testUserPassword);
        otherUser = new User("testUser2", "test2@example.com", "otherPassword");
        userService.saveUser(testUser);
        userService.saveUser(otherUser);
    }

    @AfterAll
    void tearDown() {
        userService.getAllUsers().forEach(userService::deleteUser);
    }

    @Test 
    public void testRegisterAndLoginUser() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername(testUser3Username);
        signupRequest.setEmail(testUser3Email);
        signupRequest.setPassword(testUser3Password);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LoginRequest loginRequest = new LoginRequest(testUser3Username, passwordEncoder.encode(testUser3Password));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
        )
        .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testGetAllUsers() throws Exception {
        BasicAuthToken token = new BasicAuthToken(testUser.getUsername(), testUserPassword);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(token.toAuthorizationHeader()));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").exists());
    }

    @Test
    public void testGetUserById() throws Exception {
        BasicAuthToken token = new BasicAuthToken(testUser.getUsername(), testUserPassword);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(token.toAuthorizationHeader()));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(testUser.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(testUser.getEmail()));
    }

}

