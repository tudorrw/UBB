package com.vvss.FlavorFiesta.controllers;

import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.Review;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.services.RecipeService;
import com.vvss.FlavorFiesta.services.ReviewService;
import com.vvss.FlavorFiesta.services.UserService;
import com.vvss.FlavorFiesta.util.OwnershipUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/reviews")
@Slf4j
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private OwnershipUtil ownershipUtil;

    @GetMapping("/")
    public List<Review> getReviewsBy(@RequestParam(required = false) Long ownerId, @RequestParam(required = false) Long recipeId) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        User owner = userService.getUserById(ownerId);

        if (owner != null && recipe != null) {
            return reviewService.getReviewsByOwnerAndRecipe(owner, recipe);
        }

        if (recipe != null) {
            return reviewService.getReviewsByRecipe(recipe);
        }

        if (owner != null) {
            return reviewService.getReviewsByOwner(owner);
        }
        return new ArrayList<>();
    }


    @PostMapping
    public ResponseEntity<Review> addReview(@Valid @RequestBody Review review) {
        Review savedReview = reviewService.addReview(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        Review review = reviewService.getReviewById(id);
        if (review != null) {
            if (ownershipUtil.loggedInUserIsReviewOwner(review)) {
                reviewService.deleteReview(review);
            } else {
                throw new ResponseStatusException(FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
    }
}
