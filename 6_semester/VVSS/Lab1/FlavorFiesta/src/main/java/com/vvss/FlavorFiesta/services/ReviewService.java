package com.vvss.FlavorFiesta.services;

import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.Review;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review getReviewById(long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByOwner(User owner) {
        return reviewRepository.findByOwner(owner);
    }

    public List<Review> getReviewsByRecipe(Recipe recipe) {
        return reviewRepository.findByRecipe(recipe);
    }

    public List<Review> getReviewsByOwnerAndRecipe(User owner, Recipe recipe) {
        return reviewRepository.findByOwnerAndRecipe(owner, recipe);
    }

    public void deleteReview(Review review) {
        reviewRepository.delete(review);
    }
}
