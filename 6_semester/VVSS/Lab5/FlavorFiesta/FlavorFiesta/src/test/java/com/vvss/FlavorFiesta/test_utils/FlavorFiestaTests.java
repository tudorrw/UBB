package com.vvss.FlavorFiesta.test_utils;

import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.services.RecipeService;
import com.vvss.FlavorFiesta.services.UserService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlavorFiestaTests {

    @LocalServerPort
    private int port;

    private String baseURL;
    private WebDriver driver;

    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    private User testUser;
    private Recipe testRecipe;
    private final String testPassword = "test";

    @BeforeAll
    void setup() {
        baseURL = "http://localhost:" + port;

        // Create mock user and recipe
        testUser = new User("john", "john@test.com", testPassword);
        userService.saveUser(testUser);

        testRecipe = new Recipe(testUser, "Test Pasta", "noodles, sauce", "Boil pasta and add sauce.");
        recipeService.saveRecipe(testRecipe);

        driver = new ChromeDriver();
    }

    @AfterAll
    void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    void testSuccessfulLogin() {
        driver.get(baseURL + "/login");

        driver.findElement(By.id("username")).sendKeys("john");
        driver.findElement(By.id("password")).sendKeys(testPassword);
        driver.findElement(By.id("submit-button")).click();

        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.urlContains("/home"));

        Assertions.assertTrue(driver.getCurrentUrl().contains("/home"));
    }

    @Test
    @Order(2)
    void testFailedLogin() {
        driver.get(baseURL + "/login");

        driver.findElement(By.id("username")).sendKeys("john");
        driver.findElement(By.id("password")).sendKeys("wrongpass");
        driver.findElement(By.id("submit-button")).click();

        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.urlToBe(baseURL + "/login"));

        Assertions.assertEquals(baseURL + "/login", driver.getCurrentUrl());
    }

    @Test
    @Order(3)
    void testRecipesVisibleOnHomepage() {
        loginAsTestUser();
        driver.get(baseURL + "/home");

        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("card-header")));

        WebElement recipeTitle = driver.findElement(By.className("card-header"));
        Assertions.assertTrue(recipeTitle.getText().contains("Test Pasta"));
    }


    @Test
    @Order(4)
    void testPostCommentSuccessfully() {
        loginAsTestUser();
        driver.get(baseURL + "/recipe/" + testRecipe.getId());

        WebElement commentBox = driver.findElement(By.id("comment"));
        commentBox.sendKeys("Loved it!");

        WebElement form = driver.findElement(By.id("add-comment-form"));
        form.submit();

        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Loved it!"));

        Assertions.assertTrue(driver.getPageSource().contains("Loved it!"));
    }
    @Test
    @Order(5)
    void testAccessNonexistentRecipeShows403() {
        driver.get(baseURL + "/recipe/123123");

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();

        Assertions.assertTrue(
                pageSource.contains("Access to localhost was denied") ||
                        pageSource.contains("HTTP ERROR 403") ||
                        driver.getCurrentUrl().contains("/403")
        );
    }

    @Test
    @Order(6)
    void testPostEmptyCommentFailsGracefully() {
        loginAsTestUser();
        driver.get(baseURL + "/recipe/" + testRecipe.getId());

        WebElement commentBox = driver.findElement(By.id("comment"));
        commentBox.sendKeys(""); // intentionally empty

        WebElement form = driver.findElement(By.id("add-comment-form"));
        form.submit();

        // Stay on the same page or show alert – assuming no redirect
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("comment")));

        Assertions.assertTrue(driver.getCurrentUrl().contains("/recipe/" + testRecipe.getId()));
    }

    private void loginAsTestUser() {
        driver.get(baseURL + "/login");

        driver.findElement(By.id("username")).sendKeys("john");
        driver.findElement(By.id("password")).sendKeys(testPassword);
        driver.findElement(By.id("submit-button")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/home"));
    }
}
