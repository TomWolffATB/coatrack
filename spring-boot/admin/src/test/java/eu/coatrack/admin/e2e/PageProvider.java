package eu.coatrack.admin.e2e;

import eu.coatrack.admin.e2e.api.LoginPage;
import org.openqa.selenium.WebDriver;

public class PageProvider {

    private static String authenticationCookie;

    private WebDriver driver;

    public PageProvider(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage getLoginPage(){
        return new LoginPage(driver);
    }

    public void close(){
        driver.close();
    }
}
