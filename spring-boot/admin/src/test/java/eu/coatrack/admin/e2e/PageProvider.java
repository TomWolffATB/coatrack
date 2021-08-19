package eu.coatrack.admin.e2e;

import eu.coatrack.admin.e2e.api.ServiceOfferingsSetup.ServiceOfferings;
import eu.coatrack.admin.e2e.api.Tutorial;
import org.openqa.selenium.WebDriver;

public class PageProvider {

    public static final String pathPrefix = "http://localhost:8080";
    private static String authenticationCookie;

    private final WebDriver driver;

    public PageProvider(WebDriver driver) {
        this.driver = driver;
        driver.get(pathPrefix + "/test-user-login?testUserId=verySecretId");
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
