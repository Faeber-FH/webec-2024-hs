package ch.fhnw.webec.contactlist.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static java.lang.Thread.sleep;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class FirstTest {
    private WebDriver driver;

    @LocalServerPort
    int port;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setupTest() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    void testEnoughLinksIT() {
        driver.get("http://localhost:" + port + "/contacts");
        var count = driver.findElements(By.tagName("a")).stream().filter(webElement -> webElement.getAttribute("href").contains("contacts/")).count();
        assert count == 30;
    }

    @Test
    void testClickFourthLinkIT() {
        driver.get("http://localhost:" + port + "/contacts");
        driver.findElements(By.tagName("a")).get(3).click();
        assert driver.getCurrentUrl().contains("contacts/1");
    }
}
