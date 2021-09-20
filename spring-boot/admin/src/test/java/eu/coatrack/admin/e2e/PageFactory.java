package eu.coatrack.admin.e2e;

import eu.coatrack.admin.e2e.api.ServiceOfferingsSetup.ServiceOfferings;
import eu.coatrack.admin.e2e.api.Tutorial;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static eu.coatrack.admin.e2e.CookieInjector.injectAuthenticationCookieToDriver;

public class PageFactory {

    public static final String pathPrefix = "http://localhost:8080";
    private final WebDriver driver;

    public PageFactory(WebDriver driver) {
        this.driver = driver;
        injectAuthenticationCookieToDriver(driver);
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

    public void loginWithCookieAndClickTurorial() {
        driver.get(pathPrefix + "/");
        driver.findElement(By.cssSelector("ul:nth-child(1) > li:nth-child(4) > a")).click();
        driver.get(pathPrefix + "/admin");
        driver.findElement(By.linkText("Tutorial")).click();
    }
}
