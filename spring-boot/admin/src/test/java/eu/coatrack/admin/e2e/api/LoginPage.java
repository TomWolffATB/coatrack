package eu.coatrack.admin.e2e.api;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static eu.coatrack.admin.e2e.configuration.PageConfiguration.getAdminDashboardUrl;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.getStartpageUrl;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void loginToGithub(String username, String password){
        driver.get(getStartpageUrl());
        driver.findElement(By.cssSelector("ul:nth-child(1) > li:nth-child(4) > a")).click();
        driver.findElement(By.id("login_field")).click();
        driver.findElement(By.id("login_field")).sendKeys(username);
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.name("commit")).click();
        driver.get(getAdminDashboardUrl());
    }

}
