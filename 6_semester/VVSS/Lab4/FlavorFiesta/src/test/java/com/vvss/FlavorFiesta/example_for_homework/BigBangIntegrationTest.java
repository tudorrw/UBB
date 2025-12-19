package com.vvss.FlavorFiesta.example_for_homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.Review;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.payloads.request.LoginRequest;
import com.vvss.FlavorFiesta.payloads.request.SignupRequest;
import com.vvss.FlavorFiesta.security.basicauth.BasicAuthToken;
import com.vvss.FlavorFiesta.services.RecipeService;
import com.vvss.FlavorFiesta.services.ReviewService;
import com.vvss.FlavorFiesta.services.UserService;
import com.vvss.FlavorFiesta.test_utils.TestControllerIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BigBangIntegrationTest extends TestControllerIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterAll
    void tearDown() {
        // Clean up all test data
        reviewService.getAllReviews().forEach(reviewService::deleteReview);
        recipeService.getAllRecipes().forEach(recipeService::deleteRecipe);
        userService.getAllUsers().forEach(userService::deleteUser);
    }

    @Test
    public void testBigBangIntegration() throws Exception {
        // Module A: User Management - Register and Login
        String username = "testuser";
        String password = "password";
        
        // First, register the user through the endpoint
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername(username);
        signupRequest.setEmail("test@example.com");
        signupRequest.setPassword(password);

        String responseEntity = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("Response entity: " + responseEntity);


        LoginRequest loginRequest = new LoginRequest(username, passwordEncoder.encode(password));
        String credentials = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

        String token = BasicAuthToken.fromString(credentials).toAuthorizationHeader();
        // The login response contains username:password, use it to create the auth token
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(token));

        // Module B: Recipe Management - Create a recipe
        User currentUser = userService.getUserByUsername(username);
        Recipe newRecipe = new Recipe(currentUser, "Test Recipe", "Test ingredients", "Test instructions");
        
        String recipeJson = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/recipes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                        .content(objectMapper.writeValueAsString(newRecipe))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Recipe savedRecipe = objectMapper.readValue(recipeJson, Recipe.class);

        // Module C: Review Management - Add a review
        Review newReview = new Review();
        newReview.setOwner(currentUser);
        newReview.setRecipe(savedRecipe);
        newReview.setRating(5);
        newReview.setComment("Excellent recipe!");
        newReview.setAnonymous(false);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/reviews")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newReview))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value("Excellent recipe!"));

        // Verify the complete integration
//         mockMvc.perform(
//                 MockMvcRequestBuilders.get("/api/reviews")
//                         .param("recipeId", savedRecipe.getId().toString())
//                         .headers(headers)
//                         .contentType(MediaType.APPLICATION_JSON)
//         )
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
//                 .andExpect(MockMvcResultMatchers.jsonPath("$[0].rating").value(5))
//                 .andExpect(MockMvcResultMatchers.jsonPath("$[0].comment").value("Excellent recipe!"));
    }
}
