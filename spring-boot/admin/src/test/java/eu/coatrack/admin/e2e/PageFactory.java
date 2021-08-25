package eu.coatrack.admin.e2e;

import eu.coatrack.admin.e2e.api.LoginPage;
import eu.coatrack.admin.e2e.api.ServiceOfferingsSetup.ServiceOfferings;
import eu.coatrack.admin.e2e.api.Tutorial;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class PageFactory {

    public static final String pathPrefix = "http://localhost:8080";
    private static Set<Cookie> authenticationCookies;

    private static String username = "admin";
    private static String password = "password";

    private final WebDriver driver;

    public PageFactory(WebDriver driver) {
        this.driver = driver;
        prepareAuthenticationCookie();
    }

    private void prepareAuthenticationCookie() {
        if (authenticationCookies == null){
            //This is meant for testing production builds. It gives the tester time to enter his credentials.
            //waitSecondsForUserToEnterHisCredentials(30);

            //Extra fast login testing. Please, fill in the username and password fields.
            new LoginPage(driver).loginToGithub(username, password);

            //TODO I need to find out which cookie(s) are required for being authenticated in a new browser session.
            authenticationCookies = driver.manage().getCookies();
        } else {
            authenticationCookies.forEach(cookie -> driver.manage().addCookie(cookie));
        }
    }

    private void waitSecondsForUserToEnterHisCredentials(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (Exception ignored) {}
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
