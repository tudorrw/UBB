package com.vvss.FlavorFiesta.example_for_homework;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class IncrementalIntegrationModuleBTest extends TestControllerIntegrationTest {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private String testUserPassword = "password";
    private User testUser;
    private User otherUser;
    private List<Recipe> testRecipes;

    @BeforeAll
    void setUp() {
        // First integrate with Module A (User Management)
        testUser = new User("recipeTestUser", "recipe@test.com", testUserPassword);
        otherUser = new User("otherUser", "other@test.com", "otherPassword");
        userService.saveUser(testUser);
        userService.saveUser(otherUser);

        // Create test recipes
        testRecipes = new ArrayList<>();
        testRecipes.add(new Recipe(testUser, "Test Recipe 1", "Ingredients 1", "Instructions 1"));
        testRecipes.add(new Recipe(testUser, "Test Recipe 2", "Ingredients 2", "Instructions 2"));
        testRecipes.add(new Recipe(otherUser, "Other Recipe", "Other Ingredients", "Other Instructions"));
        testRecipes.forEach(recipeService::saveRecipe);
    }

    @AfterAll
    void tearDown() {
        recipeService.getAllRecipes().forEach(recipeService::deleteRecipe);
        userService.getAllUsers().forEach(userService::deleteUser);
    }
    @Test
    public void testCreateRecipe() throws Exception {

        BasicAuthToken token = new BasicAuthToken(testUser.getUsername(), testUserPassword);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(token.toAuthorizationHeader()));

        Recipe newRecipe = new Recipe(testUser, "New Recipe", "New Ingredients", "New Instructions");
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/recipes/")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .content(objectMapper.writeValueAsString(newRecipe))
                )
            .andExpect(MockMvcResultMatchers.status().isOk());
                    
    }

    @Test
    public void testGetAllRecipes() throws Exception {
        BasicAuthToken token = new BasicAuthToken(testUser.getUsername(), testUserPassword);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(token.toAuthorizationHeader()));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/recipes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(4));
    }

    @Test
    public void testGetRecipeById() throws Exception {
        Recipe firstRecipe = testRecipes.get(0);
        BasicAuthToken token = new BasicAuthToken(testUser.getUsername(), testUserPassword);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(token.toAuthorizationHeader()));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/recipes/" + firstRecipe.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(firstRecipe.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ingredients").value(firstRecipe.getIngredients()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.instructions").value(firstRecipe.getInstructions()));
    }

   
}
