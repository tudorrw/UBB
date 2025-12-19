package com.vvss.FlavorFiesta.selenium;

import com.vvss.FlavorFiesta.models.Recipe;
import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.services.RecipeService;
import com.vvss.FlavorFiesta.services.UserService;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FlavorFiestaSeleniumTest {

    @LocalServerPort
    private int port;

    private String baseURL;
    private String loginPageURL;
    private String homePageURL;
    private String recipePageURL;
    private WebDriver driver;

    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    private User testUser;
    private Recipe testRecipe;
    private final String testUserPassword = "password";

    @BeforeAll
    void setup() {
        baseURL = "http://localhost:" + port;
        loginPageURL = baseURL + "/login";
        homePageURL = baseURL + "/home";
        recipePageURL = baseURL + "/recipe";

        // Create test user and recipe
        testUser = new User("john", "johndoe@gmail.com", testUserPassword);
        userService.saveUser(testUser);

        testRecipe = new Recipe(testUser, "Test Mici", "beef, spices, gratar", "start fire, put mici on gratar");
        recipeService.saveRecipe(testRecipe);

        driver = new ChromeDriver();
    }

    @AfterAll
    void tearDown() {
        userService.getAllUsers().forEach(userService::saveUser);

        // Close the browser
        driver.close();
        driver.quit();
    }

    @Test
    @Order(1)
    void testSuccessfulLogin() {
        driver.get(loginPageURL);

        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));

        // Enter the username and password
        usernameField.sendKeys(testUser.getUsername());
        passwordField.sendKeys(testUserPassword);

        // Submit the form (assuming there's a submit button)
        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Wait for up to 10 seconds
        wait.until(ExpectedConditions.urlContains(homePageURL)); // Wait until the URL contains the home page url

        assertTrue(driver.getCurrentUrl().contains("/home"));
    }

    @Test
    @Order(2)
    void testFailedLogin() {
        driver.get(loginPageURL);

        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));

        // Enter the username and password
        usernameField.sendKeys(testUser.getUsername());
        passwordField.sendKeys("wrong_password");

        // Submit the form (assuming there's a submit button)
        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();

        // Wait for the page to load after the submit button click
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Wait for up to 10 seconds
        wait.until(ExpectedConditions.urlContains(loginPageURL)); // Wait until the URL contains the home page url

        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @Order(3)
    void testViewRecipeDetails() {
        loginAsTestUser();
        driver.get(recipePageURL + "/" + testRecipe.getId());

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement recipeTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("text-center")));

        assertTrue(recipeTitle.getText().contains(testRecipe.getTitle()));
        assertTrue(driver.getPageSource().contains(testRecipe.getIngredients()));
        assertTrue(driver.getPageSource().contains(testRecipe.getInstructions()));
    }

    @Test
    @Order(4)
    void testAccessNonexistentRecipe() {
        loginAsTestUser();
        driver.get(recipePageURL + "/999999"); // Non-existent recipe ID

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String pageSource = driver.getPageSource();
        assertTrue(
            pageSource.contains("Access to localhost was denied") ||
            pageSource.contains("HTTP ERROR 403") ||
            driver.getCurrentUrl().contains("/403")
        );
    }

    @Test
    @Order(5)
    void testAddCommentToRecipe() {
        loginAsTestUser();
        driver.get(recipePageURL + "/" + testRecipe.getId());

        WebElement commentBox = driver.findElement(By.id("comment"));
        commentBox.sendKeys("Great recipe from Selenium test!");

        WebElement form = driver.findElement(By.id("add-comment-form"));
        form.submit();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Great recipe from Selenium test!"));

        assertTrue(driver.getPageSource().contains("Great recipe from Selenium test!"));
    }

    @Test
    @Order(6)
    void testAddEmptyComment() {
        loginAsTestUser();
        driver.get(recipePageURL + "/" + testRecipe.getId());

        WebElement commentBox = driver.findElement(By.id("comment"));
        commentBox.sendKeys("");

        WebElement form = driver.findElement(By.id("add-comment-form"));
        form.submit();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("comment")));

        assertTrue(driver.getCurrentUrl().contains("/recipe/" + testRecipe.getId()));
    }

    private void loginAsTestUser() {
        driver.get(loginPageURL);

        // Find the username and password input fields by their IDs
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));

        // Enter the username and password
        usernameField.sendKeys(testUser.getUsername());
        passwordField.sendKeys(testUserPassword);

        // Submit the form (assuming there's a submit button)
        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();

        // Wait for the page to load after the submit button click
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Wait for up to 10 seconds
        wait.until(ExpectedConditions.urlContains(homePageURL)); // Wait until the URL contains the home page url
    }
}
