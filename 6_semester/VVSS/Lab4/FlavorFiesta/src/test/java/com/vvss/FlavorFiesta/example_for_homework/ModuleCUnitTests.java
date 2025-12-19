package com.vvss.FlavorFiesta.example_for_homework;

import com.vvss.FlavorFiesta.controllers.ReviewController;
import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.Review;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.services.RecipeService;
import com.vvss.FlavorFiesta.services.ReviewService;
import com.vvss.FlavorFiesta.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ModuleCUnitTests {

    @Mock
    private ReviewService reviewService;

    @Mock
    private RecipeService recipeService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReviewController reviewController;

    private User user1;
    private User user2;
    private Recipe recipe;
    private Review review1;
    private Review review2;

    @BeforeEach
    void setUp() {
        // Setup users
        user1 = new User();
        user1.setId(1L);
        user1.setUsername("testRecipeUser");
        
        user2 = new User();
        user2.setId(2L);
        user2.setUsername("testReviewUser");

        // Setup recipe
        recipe = new Recipe(user1, "Test Recipe", "Test Ingredients", "Test Instructions");
        recipe.setId(1L);

        // Setup reviews
        review1 = new Review();
        review1.setId(1L);
        review1.setRating(5);
        review1.setComment("Excellent recipe!");
        review1.setOwner(user2);
        review1.setRecipe(recipe);
        review1.setAnonymous(false);

        review2 = new Review();
        review2.setId(2L);
        review2.setRating(4);
        review2.setComment("Very good");
        review2.setOwner(user2);
        review2.setRecipe(recipe);
        review2.setAnonymous(false);
    }

    @Test
    void testGetReviewsByRecipe() {
        // Mock service behavior
        when(recipeService.getRecipeById(recipe.getId())).thenReturn(recipe);
        when(reviewService.getReviewsByRecipe(any(Recipe.class))).thenReturn(Arrays.asList(review1, review2));

        // Call controller method with query parameter
        List<Review> response = reviewController.getReviewsBy(null, recipe.getId());

        // Verify response
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(5, response.get(0).getRating());
        assertEquals(4, response.get(1).getRating());
        assertTrue(response.stream().allMatch(review -> review.getRecipe().getId().equals(recipe.getId())));

    }

    @Test
    void testGetReviewsByOwner() {
        // Mock service behavior
        when(userService.getUserById(user2.getId())).thenReturn(user2);
        when(reviewService.getReviewsByOwner(any(User.class))).thenReturn(Arrays.asList(review1, review2));

        // Call controller method with query parameter
        List<Review> response = reviewController.getReviewsBy(user2.getId(), null);

        // Verify response
        assertNotNull(response);
        assertEquals(2, response.size());
        assertTrue(response.stream().allMatch(review -> review.getOwner().getId().equals(user2.getId())));

    }

    @Test
    void testAddReview() {
        // Mock service behavior
        when(reviewService.addReview(any(Review.class))).thenReturn(review1);

        // Call controller method
        ResponseEntity<Review> responseEntity = reviewController.addReview(review1);

        // Verify response
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Review response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(5, response.getRating());
        assertEquals("Excellent recipe!", response.getComment());
        assertEquals(user2.getId(), response.getOwner().getId());
        assertEquals(recipe.getId(), response.getRecipe().getId());

    }
} 