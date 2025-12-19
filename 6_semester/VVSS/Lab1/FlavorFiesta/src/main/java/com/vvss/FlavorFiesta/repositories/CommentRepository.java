package com.vvss.FlavorFiesta.repositories;

import com.vvss.FlavorFiesta.models.Comment;
import com.vvss.FlavorFiesta.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByRecipe(Recipe recipe);

    void deleteAllByRecipe(Recipe recipe);
}
