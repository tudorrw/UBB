# Flavor Fiesta0a

Flavor Fiesta is a Java Spring application designed for recipe sharing and community interaction. This documentation provides an overview of the project structure, entities, endpoints, security features, and deployment details.

## Table of Contents

1. [Project Overview](#project-overview)
2. [Entities](#Entities)
    - User
    - Recipe
    - Comment
    - Review
3. [Endpoints](#endpoints)
    - User Management
    - Recipe Management
    - Comment Management
    - Review Management
    - Ranking
4. [Security Features](#security-features)
5. [Deployment](#deployment)

## Project Overview

Flavor Fiesta is a Java Spring application that allows users to share and discover recipes, post comments, and rate recipes. Users can create an account, log in, and interact with the community by posting, commenting, and rating recipes.

### Entities

The project consists of four main entities:

#### User

- Represents a registered user of the application.
- Properties include username, password (encoded using Caesar cipher with a predefined shift), roles, and other user details.
- Users can log in, post recipes, comments, and reviews.

#### Recipe

- Represents a recipe shared by a user.
- Contains details such as recipe name, ingredients, instructions, and ratings.
- Users can post and delete their own recipes.

#### Comment

- Represents a comment posted by a user on a recipe.
- Contains the comment text and a reference to the associated recipe.
- Users can post and delete their own comments.

#### Review

- Represents a review/rating given by a user to a recipe.
- Contains the rating value and a reference to the associated recipe.
- Users can post and delete their own reviews.

### Endpoints

The project exposes various endpoints for user management, recipe management, comment management, review management, and ranking.

#### User Management

- `/api/users`: GET (retrieve all users), POST (create a new user)
- `/api/users/{id}`: GET (retrieve a user by ID), DELETE (delete a user by ID)
- `/api/users/{id}/recipes`: GET (retrieve recipes posted by a user), POST (post a new recipe for a user)

#### Recipe Management

- `/api/recipes`: GET (retrieve all recipes), POST (create a new recipe)
- `/api/recipes/{id}`: GET (retrieve a recipe by ID), DELETE (delete a recipe by ID)
- `/api/recipes/?ownerId=123`: GET (retrieve all recipes posted by user with id=`ownerId`)

#### Review Management

- `/api/reviews`: GET (retrieve all reviews), POST (create a new review)
- `/api/reviews/{id}`: GET (retrieve a review by ID), DELETE (delete a review by ID)
- `/api/reviews/?ownerId={userId}&recipeId={recipeId}`: GET (retrieve all recipes posted by user with id=`ownerId` or belonging to recipe with id=`recipeId`)

#### Comment Management

- `/api/comments`: GET (retrieve all comments), POST (create a new comment)
- `/api/comments/{id}`: GET (retrieve a comment by ID), DELETE (delete a comment by ID)
- `/api/comments/?ownerId={userId}&recipeId={recipeId}`: GET (retrieve all comments posted by user with id=`ownerId` or belonging to recipe with id=`recipeId`)


#### Ranking

- `/api/rankings/top-commenters`: GET (retrieve the users with most comments)
- `/api/rankings/top-reviewers`: GET (retrieve the users with most reviews)
- `/api/rankings/top-recipies`: GET (retrieve highest rated recipes)
- `/api/rankings/top-haters`: GET (retrieve the users with the most low-rated reviews)

### Security Features

- Endpoints are protected using basic authentication.
- Users authenticate using their credentials, which are base64 encoded and set in the `Authorization` header, using the format `<username>:<password>`. E.g. for the user `john` which has the password `my_password` the credentials will be: `john:my_password`. The respective `Authorization` header value will be: `Basic am9objpteV9wYXNzd29yZA==`
- User passwords are encoded using the Caesar cipher with a predefined shift.
- Access control rules are enforced to restrict users from deleting entities they do not own. Only admin users can delete other users.

### Deployment

The project is containerized using Docker and can be deployed using the provided `docker-compose.yml` file. The `docker-compose.yml` file contains definitions for two databases: an application database and a test database. The test database is used during testing.
