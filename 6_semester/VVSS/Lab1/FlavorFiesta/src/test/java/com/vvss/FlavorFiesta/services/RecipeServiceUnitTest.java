package com.vvss.FlavorFiesta.services;

import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceUnitTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    public void testGetRecipesByUser() {
        // Mock data
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        Recipe recipe1 = new Recipe(user, "Recipe 1", "ingredients", "instructions");
        Recipe recipe2 = new Recipe(user, "Recipe 2", "ingredients2", "instructions2");


        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        // Mock the repository method
        when(recipeRepository.findByOwner(user)).thenReturn(recipes);

        // Call the service method
        List<Recipe> result = recipeService.getRecipesByOwner(user);

        // Verify the result
        assertEquals(2, result.size());
        assertEquals("Recipe 1", result.get(0).getTitle());
        assertEquals("Recipe 2", result.get(1).getTitle());
    }
}
