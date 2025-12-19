package com.vvss.FlavorFiesta.controllers;

import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.services.RankingService;
import com.vvss.FlavorFiesta.services.RecipeService;
import com.vvss.FlavorFiesta.services.UserService;
import com.vvss.FlavorFiesta.util.RankedItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rankings")
@Slf4j
public class RankingController {
    @Autowired
    private RankingService rankingService;


    @GetMapping("/top-commenters")
    public List<RankedItem<User>> getTopCommenters() {
        return rankingService.getUserRankingWithMostComments();
    }

    @GetMapping("/top-reviewers")
    public List<RankedItem<User>> getTopReviewers() {
        return rankingService.getUserRankingWithMostReviews();
    }

    @GetMapping("/top-recipies")
    public List<RankedItem<Recipe>> getTopRecipes() {
        return rankingService.getRankingOfRecipesByRating();
    }

    @GetMapping("/top-haters")
    public List<RankedItem<User>> getTopHaters() {
        return rankingService.getUserRankingWithLowestReviewRatings();
    }
}
