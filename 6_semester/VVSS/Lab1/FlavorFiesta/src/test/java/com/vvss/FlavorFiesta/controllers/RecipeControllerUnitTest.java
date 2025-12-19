package com.vvss.FlavorFiesta.controllers;


import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerUnitTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    @Test
    public void testGetAllRecipes() {
        User user = new User("testUser", "test@example.com", "password");

        // Mock data
        Recipe recipe1 = new Recipe(user, "Pasta Carbonara", "Spaghetti, eggs, bacon, parmesan cheese", "1. Cook spaghetti ...");
        Recipe recipe2 = new Recipe(user, "Chicken Curry", "Chicken, curry paste, coconut milk, vegetables", "1. Heat oil ...");
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);

        // Mock service method
        when(recipeService.getAllRecipes()).thenReturn(recipes);

        // Call controller method
        List<Recipe> responseEntity = recipeController.getRecipes(null);

        // Verify response
        assertEquals(2, responseEntity.size());
    }
}