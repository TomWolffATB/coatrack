package eu.coatrack.admin.e2e;

import eu.coatrack.admin.e2e.api.LoginPage;
import eu.coatrack.admin.e2e.api.ServiceOfferingsSetup.ServiceOfferings;
import eu.coatrack.admin.e2e.api.Tutorial;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class PageFactory {

    public static final String pathPrefix = "http://localhost:8080";

    private static String username = "admin";
    private static String password = "password";

    private final WebDriver driver;

    public PageFactory(WebDriver driver) {
        this.driver = driver;
        new LoginPage(driver).loginToGithub(username, password);
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
