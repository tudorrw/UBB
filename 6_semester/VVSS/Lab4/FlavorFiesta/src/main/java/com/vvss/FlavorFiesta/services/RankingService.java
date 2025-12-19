package com.vvss.FlavorFiesta.services;

import com.vvss.FlavorFiesta.models.Comment;
import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.Review;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.util.RankedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RankingService {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CommentService commentService;


    public List<RankedItem<User>> getUserRankingWithMostComments() {
        Map<User, Long> commentCounts = commentService
                .getAllComments()
                .stream()
                .collect(Collectors.groupingBy(Comment::getOwner, Collectors.counting()));
        return rankItems(commentCounts);
    }

    public List<RankedItem<User>> getUserRankingWithMostReviews() {
        Map<User, Long> reviewCounts = reviewService
                .getAllReviews()
                .stream()
                .collect(Collectors.groupingBy(Review::getOwner, Collectors.counting()));
        return rankItems(reviewCounts);
    }

    public List<RankedItem<User>> getUserRankingWithLowestReviewRatings() {
        Map<User, Long> reviewCounts = reviewService
                .getAllReviews()
                .stream()
                .collect(Collectors.groupingBy(Review::getOwner, Collectors.averagingInt(Review::getRating)))
                .entrySet()
                .stream()
                .map((entry) -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().longValue() * -1))
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        ));
        return rankItems(reviewCounts);
    }

    public List<RankedItem<Recipe>> getRankingOfRecipesByRating() {
        Map<Recipe, Long> ratings = reviewService
                .getAllReviews()
                .stream()
                .collect(Collectors.groupingBy(Review::getRecipe, Collectors.averagingInt(Review::getRating)))
                .entrySet()
                .stream()
                .map((entry) -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().longValue()))
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        ));
        return rankItems(ratings);
    }

    private <T> List<RankedItem<T>> rankItems(Map<T, Long> data) {
        return data.entrySet().stream()
                .sorted(Map.Entry.<T, Long>comparingByValue().reversed())
                .limit(10)
                .map(entry -> new RankedItem<>(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
