package com.vvss.FlavorFiesta.example_for_homework;

import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.Review;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.repositories.RecipeRepository;
import com.vvss.FlavorFiesta.repositories.ReviewRepository;
import com.vvss.FlavorFiesta.repositories.UserRepository;
import com.vvss.FlavorFiesta.services.RankingService;
import com.vvss.FlavorFiesta.services.ReviewService;
import com.vvss.FlavorFiesta.util.RankedItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ModuleDUnitTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private RankingService rankingService;

    private User user1;
    private User user2;
    private Recipe recipe1;
    private Recipe recipe2;
    private Review review1;
    private Review review2;
    private Review review3;

    @BeforeEach
    void setUp() {
        // Setup users
        user1 = new User("rankinguser1", "ranking1@example.com", "password123");
        user1.setId(1L);

        user2 = new User("rankinguser2", "ranking2@example.com", "password123");
        user2.setId(2L);

        // Setup recipes
        recipe1 = new Recipe(user1, "Ranking Test Recipe 1", "Test Ingredients 1", "Test Instructions 1");
        recipe1.setId(1L);

        recipe2 = new Recipe(user2, "Ranking Test Recipe 2", "Test Ingredients 2", "Test Instructions 2");
        recipe2.setId(2L);

        // Setup reviews
        review1 = new Review();
        review1.setId(1L);
        review1.setRating(5);
        review1.setComment("Excellent recipe!");
        review1.setOwner(user1);
        review1.setRecipe(recipe1);
        review1.setAnonymous(false);

        review2 = new Review();
        review2.setId(2L);
        review2.setRating(1);
        review2.setComment("Not good");
        review2.setOwner(user2);
        review2.setRecipe(recipe1);
        review2.setAnonymous(false);

        review3 = new Review();
        review3.setId(3L);
        review3.setRating(4);
        review3.setComment("Very good");
        review3.setOwner(user1);
        review3.setRecipe(recipe2);
        review3.setAnonymous(false);
    }

    @Test
    void testGetUserRankingWithMostReviews() {
        // Setup mock behavior
        when(reviewService.getAllReviews()).thenReturn(Arrays.asList(review1, review2, review3));

        // Execute test
        List<RankedItem<User>> result = rankingService.getUserRankingWithMostReviews();

        // Verify results
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(user1.getId(), result.get(0).getEntity().getId());
        assertEquals(2, result.get(0).getRank());
        assertEquals(user2.getId(), result.get(1).getEntity().getId());
        assertEquals(1, result.get(1).getRank());

        // Verify interactions
        verify(reviewService).getAllReviews();

    }

    @Test
    void testGetUserRankingWithLowestReviewRatings() {
        // Setup mock behavior
        when(reviewService.getAllReviews()).thenReturn(Arrays.asList(review1, review2, review3));


        // Execute test
        List<RankedItem<User>> result = rankingService.getUserRankingWithLowestReviewRatings();

        // Verify results
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(user2.getId(), result.get(0).getEntity().getId());
        assertEquals(-1, result.get(0).getRank());
        assertEquals(user1.getId(), result.get(1).getEntity().getId());
        assertEquals(-4, result.get(1).getRank());

        // Verify interactions
        verify(reviewService).getAllReviews();

    }

    @Test
    void testGetRankingOfRecipesByRating() {
        // Setup mock behavior
        when(reviewService.getAllReviews()).thenReturn(Arrays.asList(review1, review2, review3));


        // Execute test
        List<RankedItem<Recipe>> result = rankingService.getRankingOfRecipesByRating();

        // Verify results
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(recipe2.getId(), result.get(0).getEntity().getId());
        assertEquals(4, result.get(0).getRank());
        assertEquals(recipe1.getId(), result.get(1).getEntity().getId());
        assertEquals(3, result.get(1).getRank());

        // Verify interactions
        verify(reviewService).getAllReviews();

    }
}