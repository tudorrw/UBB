**Project: Java Console Application**

This document serves as a guide for the development of a console application in Java.

**Project Overview**
The project will evolve incrementally, with each step building upon the previous one. Students will work in teams of two and demonstrate an increment of the project every two weeks.
the projects attached in this folder indicate each of the increments that you can find below. 
So lab_2 refers to Increment 1, 
   lab_3 to Increment 2, 
   lab_4 to Increment 3 and 
   lab_5 to Increments 4 and 5

**Requirements**

1. Entities and their Relationships:
    a. A data model with at least 10 entities and various relationships is required. Relationships must include inheritance, composition, many-to-many relationships, one-to-many relationships, and one-to-one relationships.
2. Database Connection:
    a. A database connection must be implemented.
3. Design Patterns:
    a. Implement at least six design patterns from the following list: Strategy, Factory, Decorator, Proxy, Adapter, Observer, Builder, and Singleton.
    b. The use of the Observer pattern is mandatory.
4. REST Service:
    a. A REST controller must be implemented using Spring Boot.
5. Architecture:
    a. A well-structured architecture based on multiple layers must be defined and implemented. For example: DAO, Repository, Service, Controller, UI (console application).
    b. Each person must define the chosen architecture and be able to justify their choice.

**Increments**

The expectation is that the project will be implemented gradually in five increments and submitted accordingly, with each building on the previous one. Each increment must include at least 2 tests! The tests must ensure that the app or app components behave as expected according to the requirements of the increment.

Each increment is explained below:

**Increment 1: Setting up Scaffolding/Foundation**
- **Submission Date:** Week 5
- **Tasks:**
    - Create a simple console application.
    - Define the data model.
    - Implement an initial database connection (In-Memory Repository is recommended).
    - Establish a basic direction and purpose of the project.
- **Deliverables:**
    - Skeleton of the console application.
    - Initial data model.
    - Rudimentary database connection.

**Increment 2: Introduction of Design Patterns**
- **Submission Date:** Week 7
- **Tasks:**
    - Integration of at least 4 design patterns into the console application.
    - Observer Pattern is mandatory!
- **Results:**
    - Code demonstrating the use of design patterns.
    - Updated application with integrated patterns.

**Increment 3: Implementation of Database Connection**
- **Submission Date:** Week 9
- **Tasks:**
    - Implementation of a database connection to a real database (PostgreSQL, MySql, etc.).
    - The app must function exactly as in the previous increment, without noticing that the persistence layer now uses a database.
- **Results:**
    - Database connection.

**Increment 4: Introduction of the REST Service**
- **Submission Date:** Week 11
- **Tasks:**
    - Start integrating a REST service using Spring Boot.
    - Add 2 more design patterns.
    - ⚠️ It is not expected that you create a UI/web app for the REST server. Instead, you can use tools like Postman or IntelliJ's HTTP Request plugin.
- **Results:**
    - Code illustrating the implementation of the design patterns.
    - Initial setup of the REST service.

**Increment 5: Project Completion**
- **Submission Date:** Week 13
- **Tasks:**
    - Complete the integration of the REST service and ensure it works properly.
    - Refine the application and documentation for the final presentation. In the live demo of the application, at least 5 use cases must be presented.
- **Results:**
    - Fully functional application with integrated REST service.
    - Final documentation for presentation.
