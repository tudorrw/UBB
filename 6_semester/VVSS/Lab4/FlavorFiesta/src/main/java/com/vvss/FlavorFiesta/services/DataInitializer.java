package com.vvss.FlavorFiesta.services;

import com.vvss.FlavorFiesta.models.Comment;
import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Profile("!test")
@Component
@Slf4j
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private CommentService commentService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Check if database is empty
        long recipeCount = recipeService.countAllRecipes();
        if (recipeCount == 0) {
            // Populate database with sample data
            log.info("Populating database with sample data");

            List<User> users = Arrays.asList(
                    new User("john", "john@example.com", "my_password"),
                    new User("jessica", "jessica@example.com", "ILoveCake"),
                    new User("jack", "jack@example.com", "burgers")
            );
            users.forEach(userService::saveUser);

            List<Recipe> recipes = Arrays.asList(
                    new Recipe(users.get(0), "Spaghetti Carbonara", "Pasta, Eggs, Bacon, Parmesan Cheese", "Cook pasta, fry bacon, mix eggs and cheese, combine everything"),
                    new Recipe(users.get(1), "Chicken Curry", "Chicken, Curry Paste, Coconut Milk, Vegetables", "Fry chicken, add curry paste and coconut milk, simmer with vegetables")
            );
            recipes.forEach(recipeService::saveRecipe);

            List<Comment> comments = Arrays.asList(
                new Comment(recipes.get(0), users.get(0), "Not a great recipe"),
                new Comment(recipes.get(1), users.get(0), "Very tasty!"),
                new Comment(recipes.get(1), users.get(1), "Love it!")
            );
            comments.forEach(commentService::saveComment);


            log.info("Done populating database");
        } else {
            log.info("Skipping populating database as there are already recipes in it");
        }
    }
}
