package eu.coatrack.admin.e2e;

import eu.coatrack.admin.e2e.api.Dashboard;
import org.openqa.selenium.WebDriver;

public class PageProvider {

    public static final String pathPrefix = "http://localhost:8080";
    private static String authenticationCookie;

    private final WebDriver driver;

    public PageProvider(WebDriver driver) {
        this.driver = driver;
        driver.get(pathPrefix + "/test-user-login?testUserId=verySecretId");
    }

    public Dashboard getDashboard(){
        return new Dashboard(driver);
    }

    public void close(){
        driver.close();
    }
}
