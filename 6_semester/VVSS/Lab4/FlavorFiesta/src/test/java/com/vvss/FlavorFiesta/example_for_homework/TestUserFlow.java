package com.vvss.FlavorFiesta.example_for_homework;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.payloads.request.LoginRequest;
import com.vvss.FlavorFiesta.security.basicauth.BasicAuthToken;
import com.vvss.FlavorFiesta.services.RecipeService;
import com.vvss.FlavorFiesta.services.UserService;
import com.vvss.FlavorFiesta.test_utils.TestControllerIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;


public class TestUserFlow extends TestControllerIntegrationTest {

    User testUser;
    String testUserPassword = "password";
    List<Recipe> recipes = new ArrayList<>();

    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    void setup() {
        testUser = new User("john", "john@example.com", testUserPassword);
        userService.saveUser(testUser);

        Recipe recipe1 = new Recipe(testUser, "Pasta Carbonara", "Spaghetti, eggs, bacon, parmesan cheese", "1. Cook spaghetti ...");
        Recipe recipe2 = new Recipe(testUser, "Chicken Curry", "Chicken, curry paste, coconut milk, vegetables", "1. Heat oil ...");
        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.forEach(recipeService::saveRecipe);
    }

    @AfterAll
    void tearDown() {
        recipeService.getAllRecipes().forEach(recipeService::deleteRecipe);
        userService.getAllUsers().forEach(userService::deleteUser);
    }

    @Test
    void testLoginAndGetRecipes() throws Exception {
        LoginRequest request = new LoginRequest(testUser.getUsername(), testUserPassword);


        String credentials = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = BasicAuthToken.fromString(credentials).toAuthorizationHeader();
        
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(token));
        System.out.println("Token: " + token);
        System.out.println("Credentials: " + credentials);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/recipes/?ownerId=" + testUser.getId())
                                .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(recipes.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(recipes.get(1).getId()));
    }
}
