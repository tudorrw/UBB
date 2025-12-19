package com.vvss.FlavorFiesta.example_for_homework;

import com.vvss.FlavorFiesta.controllers.RecipeController;
import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ModuleBUnitTests {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    private User user;
    private Recipe recipe1;
    private Recipe recipe2;

    @BeforeEach
    void setUp() {
        // Setup user
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        // Setup recipes
        recipe1 = new Recipe(user, "Spaghetti Carbonara", "Pasta, Eggs, Bacon, Parmesan", "1. Cook pasta...");
        recipe1.setId(1L);

        recipe2 = new Recipe(user, "Chicken Curry", "Chicken, Curry Paste, Coconut Milk", "1. Cook chicken...");
        recipe2.setId(2L);
    }

    @Test
    void testAddRecipe() {
        // Mock service behavior
        when(recipeService.saveRecipe(any(Recipe.class))).thenReturn(recipe1);

        // Call controller method
        Recipe response = recipeController.addRecipe(recipe1);

        // Verify response
        assertNotNull(response);
        assertEquals(user.getId(), response.getOwner().getId());
        assertEquals(recipe1.getId(), response.getId());

    }


    @Test
    void testGetRecipeById() {
        // Mock service behavior
        when(recipeService.getRecipeById(1L)).thenReturn(recipe1);

        // Call controller method
        Recipe response = recipeController.getRecipeById(1L);

        // Verify response
        assertNotNull(response);
        assertEquals("Spaghetti Carbonara", response.getTitle());
        assertEquals(user.getId(), response.getOwner().getId());

        // Verify service interaction
        verify(recipeService).getRecipeById(1L);
    }

} 