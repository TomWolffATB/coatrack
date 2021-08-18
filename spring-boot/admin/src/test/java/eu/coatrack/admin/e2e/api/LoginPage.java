package eu.coatrack.admin.e2e.api;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public String someTest() {
        driver.get("https://bing.com/");
        driver.findElement(By.name("q")).sendKeys("cheese");
        driver.findElement(By.id("search_icon")).click();
        WebElement firstResult = driver.findElements(By.cssSelector("h2")).get(1);
        return firstResult.getAttribute("textContent");
    }
}