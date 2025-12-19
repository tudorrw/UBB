package com.vvss.FlavorFiesta.repositories;

import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.Review;
import com.vvss.FlavorFiesta.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByOwner(User owner);

    List<Review> findByRecipe(Recipe recipe);

    List<Review> findByOwnerAndRecipe(User owner, Recipe recipe);

    void deleteAllByRecipe(Recipe recipe);
}