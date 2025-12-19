package com.vvss.FlavorFiesta.services;

import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.repositories.RecipeRepository;
import com.vvss.FlavorFiesta.repositories.UserRepository;
import com.vvss.FlavorFiesta.test_utils.TestDBIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RecipeServiceIntegrationTest extends TestDBIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    DataSource dataSource;

    @Test
    public void testGetRecipesByOwner() throws SQLException {
        // Create test user
        User user = new User("testUser", "test@example.com", "password");
        User user2 = new User("testUser2", "test2@example.com", "password");
        userRepository.save(user);
        userRepository.save(user2);

        // Create test recipes
        Recipe recipe1 = new Recipe(user, "Recipe 1", "Ingredients for recipe 1", "Instructions for recipe 1");
        entityManager.persist(recipe1);

        Recipe recipe2 = new Recipe(user, "Recipe 2", "Ingredients for recipe 2", "Instructions for recipe 2");
        entityManager.persist(recipe2);

        Recipe recipe3 = new Recipe(user2, "Recipe 3", "Ingredients for recipe 3", "Instructions for recipe 3");
        entityManager.persist(recipe3);

        // Call the service method
        List<Recipe> recipes = recipeRepository.findByOwner(user);

        // Verify the result
        assertEquals(2, recipes.size());
        assertEquals("Recipe 1", recipes.get(0).getTitle());
        assertEquals("Recipe 2", recipes.get(1).getTitle());
    }
}