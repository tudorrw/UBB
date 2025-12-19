package com.vvss.FlavorFiesta.controllers;

import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.services.RecipeService;
import com.vvss.FlavorFiesta.services.UserService;
import com.vvss.FlavorFiesta.util.OwnershipUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/recipes")
@Slf4j
public class RecipeController {
    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private OwnershipUtil ownershipUtil;

    @GetMapping("/")
    public List<Recipe> getRecipes(@RequestParam(required = false) Long ownerId) {
        if (ownerId != null) {
            User owner = userService.getUserById(ownerId);
            if (owner == null) {
                return new ArrayList<>();
            }
            return recipeService.getRecipesByOwner(owner);
        }

        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @PostMapping("/")
    public Recipe addRecipe(@RequestBody Recipe recipe) {
        return recipeService.saveRecipe(recipe);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe != null) {
            if (ownershipUtil.loggedInUserIsRecipeOwner(recipe)) {
                recipeService.deleteRecipe(recipe);
            } else {
                throw new ResponseStatusException(FORBIDDEN, "Forbidden");
            }
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
    }
}
