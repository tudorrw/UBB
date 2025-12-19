package com.vvss.FlavorFiesta.services;

import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.repositories.CommentRepository;
import com.vvss.FlavorFiesta.repositories.RecipeRepository;
import com.vvss.FlavorFiesta.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public long countAllRecipes() {
        return recipeRepository.count();
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public List<Recipe> getRecipesByOwner(User user) {
        return recipeRepository.findByOwner(user);
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Recipe recipe) {
        commentRepository.deleteAllByRecipe(recipe);
        reviewRepository.deleteAllByRecipe(recipe);
        recipeRepository.delete(recipe);
    }
}