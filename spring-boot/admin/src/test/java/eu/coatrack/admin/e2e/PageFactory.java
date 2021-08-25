package eu.coatrack.admin.e2e;

import eu.coatrack.admin.e2e.api.ServiceOfferingsSetup.ServiceOfferings;
import eu.coatrack.admin.e2e.api.Tutorial;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.util.Set;
import java.util.logging.Level;

public class PageFactory {

    public static final String pathPrefix = "http://localhost:8080";
    private static Set<Cookie> authenticationCookies;

    private static String username = "admin";
    private static String password = "password";

    private final WebDriver driver;

    public PageFactory(WebDriver driver) {
        this.driver = driver;
        driver.get(pathPrefix + "/test-user-login?testUserId=verySecretId");

        if (authenticationCookies == null){
            //This is meant for testing production builds. Give the tester time to enter his credentials.
            /*try {
                Thread.sleep(30_000);
            } catch (Exception ignored){}*/

            //Extra fast login testing. Please, fill in username and password fields.
            driver.get(pathPrefix + "/");
            driver.findElement(By.cssSelector("ul:nth-child(1) > li:nth-child(4) > a")).click();
            driver.findElement(By.id("login_field")).click();
            driver.findElement(By.id("login_field")).sendKeys(username);
            driver.findElement(By.id("password")).click();
            driver.findElement(By.id("password")).sendKeys(password);
            driver.findElement(By.name("commit")).click();

            authenticationCookies = driver.manage().getCookies();
        } else {
            authenticationCookies.forEach(cookie -> driver.manage().addCookie(cookie));
        }
    }

    public Tutorial getTutorial(){
        return new Tutorial(driver);
    }

    public ServiceOfferings getServiceOfferings(){
        return new ServiceOfferings(driver);
    }

    public void close(){
        driver.close();
    }
}
