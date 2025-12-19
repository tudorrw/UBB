package com.vvss.FlavorFiesta.controllers;

import com.vvss.FlavorFiesta.models.Comment;
import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.Review;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.services.CommentService;
import com.vvss.FlavorFiesta.services.RecipeService;
import com.vvss.FlavorFiesta.services.ReviewService;
import com.vvss.FlavorFiesta.services.UserService;
import com.vvss.FlavorFiesta.util.OwnershipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ViewController {
    @Autowired
    RecipeService recipeService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    OwnershipUtil ownershipUtil;

    @GetMapping("/")
    public String index(Model model) {
        // Add logic to fetch and pass data to the index template
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        // Add logic to fetch and pass data to the index template
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "home";
    }

    @GetMapping("/recipe/{id}")
    public String recipe(Model model, @PathVariable("id") long recipeId) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        List<Comment> comments = commentService.getCommentsForRecipe(recipe);
        model.addAttribute("recipe", recipe);
        model.addAttribute("comments", comments);
        return "recipe";
    }

    @GetMapping("/user/{id}")
    public String user(Model model, @PathVariable("id") long userId) {
        User user = userService.getUserById(userId);
        List<Recipe> recipes = recipeService.getRecipesByOwner(user);
        List<Review> reviews = reviewService.getReviewsByOwner(user);
        model.addAttribute("reviews", reviews);
        model.addAttribute("userRecipes", recipes);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User user = ownershipUtil.getCurrentUser();
        List<Recipe> recipes = recipeService.getRecipesByOwner(user);
        List<Review> reviews = reviewService.getReviewsByOwner(user);
        model.addAttribute("reviews", reviews);
        model.addAttribute("userRecipes", recipes);
        model.addAttribute("user", user);
        return "profile";
    }

}
