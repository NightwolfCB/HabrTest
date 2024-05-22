package com.example.habrtest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

public class MainPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.habr.com/");

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void registrationTest() {
        // Finding login button on the main page
        WebElement loginButton = driver.findElement(By.cssSelector(
                "button[class='btn btn_solid btn_small tm-header-user-menu__login']"));
        loginButton.click();
        // Scenario where user don't have account and wants to create a new one
        WebElement signUp = driver.findElement(By.xpath("//*[contains(text(), 'Sign up')]"));
        signUp.click();
        // On sign-up page there should be registration mention
        assertTrue(driver.findElement(By.xpath("//*[contains(text(), 'Registration')]")).isDisplayed(),
                "Registration не найдена" );
    }

    @Test
    public void paginationTest() {
        // "ARTICLE" and "NEWS" posts have <article> tag and class="tm-articles-list__item"
        List<WebElement> posts = driver.findElements(By.cssSelector("article[class='tm-articles-list__item']"));
        // "POST" posts have class="tm-articles-list__item tm-articles-list__item_no-padding" which also counts
        // Separated small news block section have class="tm-articles-list__after-article" which counts too
        // Overall each page has 20 posts summary
        assertEquals(20, posts.size(), "Posts count on the page not equal 20");
    }

}
