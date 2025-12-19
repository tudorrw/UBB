package com.vvss.FlavorFiesta.example_for_homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.Review;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.security.basicauth.BasicAuthToken;
import com.vvss.FlavorFiesta.services.RecipeService;
import com.vvss.FlavorFiesta.services.ReviewService;
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
public class IncrementalIntegrationModuleCTest extends TestControllerIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    private String testUserPassword = "password";
    private User testUser;
    private User otherUser;
    private Recipe testRecipe;
    private List<Review> testReviews;

    @BeforeAll
    void setUp() {
        // First integrate with Module A (User Management)
        testUser = new User("reviewTestUser", "review@test.com", testUserPassword);
        otherUser = new User("otherReviewUser", "otherreview@test.com", "otherPassword");
        testUser = userService.saveUser(testUser);
        otherUser = userService.saveUser(otherUser);

        // Then integrate with Module B (Recipe Management)
        testRecipe = new Recipe(testUser, "Test Recipe", "Test Ingredients", "Test Instructions");
        recipeService.saveRecipe(testRecipe);

        // Create test reviews
        testReviews = new ArrayList<>();
        Review review = new Review();
        review.setOwner(testUser);
        review.setRecipe(testRecipe);
        review.setRating(5);
        review.setComment("Great recipe!");
        review.setAnonymous(false);
        testReviews.add(review);
     
        testReviews.forEach(reviewService::addReview);
    }

    @AfterAll
    void tearDown() {
        reviewService.getAllReviews().forEach(reviewService::deleteReview);
        recipeService.getAllRecipes().forEach(recipeService::deleteRecipe);
        userService.getAllUsers().forEach(userService::deleteUser);
    }


    @Test
    public void testGetReviewsByRecipeAndOwner() throws Exception {
        BasicAuthToken token = new BasicAuthToken(testUser.getUsername(), testUserPassword);
    
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/reviews/")               // same path
                        .param("recipeId", String.valueOf(testRecipe.getId()))
                        .param("ownerId",  String.valueOf(testUser.getId()))   // ← add this line
                        .header(HttpHeaders.AUTHORIZATION, token.toAuthorizationHeader())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].rating").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].comment").value("Great recipe!"));
    }
    

    @Test
    public void testCreateReview() throws Exception {
        BasicAuthToken token = new BasicAuthToken(otherUser.getUsername(), "otherPassword");
        System.out.println("Token: " + token);
        System.out.println("Token2: " + token.toAuthorizationHeader());
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization", List.of(token.toAuthorizationHeader()));

        Review newReview = new Review();
        newReview.setOwner(otherUser);
        newReview.setRecipe(testRecipe);
        newReview.setRating(4);
        newReview.setComment("Good recipe!");
        newReview.setAnonymous(false);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/reviews")
                        .content(objectMapper.writeValueAsString(newReview))
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value("Good recipe!"));
    }
}
