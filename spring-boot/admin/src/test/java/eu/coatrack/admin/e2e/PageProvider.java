package eu.coatrack.admin.e2e;

import eu.coatrack.admin.e2e.api.ServiceOfferingsSetup.ServiceOfferings;
import eu.coatrack.admin.e2e.api.Tutorial;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class PageProvider {

    public static final String pathPrefix = "http://localhost:8080";
    private static Set<Cookie> authenticationCookies;

    private final WebDriver driver;

    public PageProvider(WebDriver driver) {
        this.driver = driver;
        driver.get(pathPrefix + "/test-user-login?testUserId=verySecretId");

        if (authenticationCookies == null){
            driver.get(pathPrefix + "/admin");

            try {
                Thread.sleep(30000);
            } catch (Exception e) {}

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
