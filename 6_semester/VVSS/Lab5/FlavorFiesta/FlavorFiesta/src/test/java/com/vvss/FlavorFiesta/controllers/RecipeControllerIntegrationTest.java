package com.vvss.FlavorFiesta.controllers;

import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class RecipeControllerIntegrationTest {
    /*
    Test the integration between RecipeService and RecipeController
     */

    @InjectMocks
    private RecipeController recipeController;


    @Mock
    private RecipeService recipeService;

    @Test
    public void testFindRecipeById() throws Exception {
        /*
        Make sure that the RecipeController is using the RecipeService.getRecipeById method
         */
        User user = new User();
        Recipe expectedRecipe = new Recipe(user, "some title", "ingredients", "instructions");
        expectedRecipe.setId(123L);
        Mockito.when(recipeService.getRecipeById(expectedRecipe.getId())).thenReturn(expectedRecipe);

        Recipe actualRecipe = recipeController.getRecipeById(expectedRecipe.getId());
        assert actualRecipe == expectedRecipe;
    }
}
