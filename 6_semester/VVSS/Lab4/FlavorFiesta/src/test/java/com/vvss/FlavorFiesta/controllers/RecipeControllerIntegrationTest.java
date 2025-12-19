package com.vvss.FlavorFiesta.controllers;

import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.security.basicauth.BasicAuthToken;
import com.vvss.FlavorFiesta.services.RecipeService;
import com.vvss.FlavorFiesta.services.UserService;
import com.vvss.FlavorFiesta.test_utils.TestControllerIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecipeControllerIntegrationTest extends TestControllerIntegrationTest {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    private String testUserPassword = "password";
    private User testUser;

    @BeforeAll
    void setUp() {
        // Test data
        testUser = new User("john", "john@example.com", testUserPassword);
        User otherUser = new User("jack", "jack@example.com", "someOtherPassword");
        userService.saveUser(testUser);
        userService.saveUser(otherUser);
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe(testUser, "Pasta Carbonara", "Spaghetti, eggs, bacon, parmesan cheese", "1. Cook spaghetti ..."));
        recipes.add(new Recipe(testUser, "Chicken Curry", "Chicken, curry paste, coconut milk, vegetables", "1. Heat oil ..."));
        recipes.add(new Recipe(otherUser, "Other Chicken Curry", "Chicken, curry paste, coconut milk, vegetables", "1. Heat oil ..."));
        recipes.forEach(recipeService::saveRecipe);
    }

    @AfterAll
    void tearDown(){
        recipeService.getAllRecipes().forEach(recipeService::deleteRecipe);
        userService.getAllUsers().forEach(userService::deleteUser);
    }

    @Test
    public void testFindRecipesByOwner() throws Exception {
        BasicAuthToken token = new BasicAuthToken(testUser.getUsername(), testUserPassword);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(token.toAuthorizationHeader()));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/recipes/?ownerId=" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }
}
