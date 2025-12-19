package com.vvss.FlavorFiesta.ui;

import com.vvss.FlavorFiesta.models.User;
import com.vvss.FlavorFiesta.services.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //  meant to be used together with and @BeforeAll @AfterAll
public class TestWebApp {

    @LocalServerPort
    private int port;

    private String baseURL;
    private String loginPageURL;
    private String homePageURL;
    private WebDriver driver;

    @Autowired
    UserService userService;

    User testUser;
    String testUserPassword = "test";


    // change to @BeforeEach if you need to reset the browser before each test
    @BeforeAll
    void setup() {
        baseURL = "http://localhost:" + port;
        loginPageURL = baseURL + "/login";
        homePageURL = baseURL + "/home";

        testUser = new User("john", "john@test.com", testUserPassword);
        userService.saveUser(testUser);

        driver = new ChromeDriver();
    }

    // change to @AfterEach if you need to reset the browser after each test
    @AfterAll
    void tearDown() {
        userService.getAllUsers().forEach(userService::saveUser);

        // Close the browser
        driver.close();
        driver.quit();
    }

    @Test
    void testSuccessfulLogin() throws Exception {
        // Navigate to login page
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait for up to 10 seconds
        wait.until(ExpectedConditions.urlContains(homePageURL)); // Wait until the URL contains the home page url


        // Check that the sign out button exists
        WebElement signOutButton = driver.findElement(By.id("sign-out"));
        assert signOutButton != null : "Sign-out button not found";
        assert signOutButton.isDisplayed() : "Sign-out button is not displayed";
    }

    @Test
    void testFailedLogin() throws Exception {
        // Navigate to login page
        driver.get(loginPageURL);

        // Find the username and password input fields by their IDs
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));

        // Enter the username and password
        usernameField.sendKeys(testUser.getUsername());
        passwordField.sendKeys("wrong password");

        // Submit the form (assuming there's a submit button)
        WebElement submitButton = driver.findElement(By.id("submit-button"));
        submitButton.click();

        // Wait for the page to load after the submit button click
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait for up to 10 seconds
        wait.until(ExpectedConditions.urlContains(loginPageURL)); // Wait until the URL contains the home page url


        // Check that the sign-out button does not exist
        try {
            WebElement signOutButton = driver.findElement(By.id("sign-out"));
            assert false : "Sign-out button was found";
        } catch (NoSuchElementException ignored) {

        }
    }
}
