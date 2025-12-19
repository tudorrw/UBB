package com.vvss.FlavorFiesta.util;

import com.vvss.FlavorFiesta.models.Comment;
import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.Review;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Component
public class OwnershipUtil {
    @Autowired
    private UserService userService;

    public boolean loggedInUserIsRecipeOwner(Recipe recipe) {
        return recipe.getOwner().getUsername().equals(getCurrentUserUsername());
    }

    public boolean loggedInUserIsCommentOwner(Comment recipe) {
        return recipe.getOwner().getUsername().equals(getCurrentUserUsername());
    }

    public boolean loggedInUserIsReviewOwner(Review recipe) {
        return recipe.getOwner().getUsername().equals(getCurrentUserUsername());
    }


    String getCurrentUserUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.getUserByUsername(username);
    }
}
