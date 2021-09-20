package eu.coatrack.admin.e2e;

import eu.coatrack.admin.e2e.api.ServiceOfferingsSetup.ServiceOfferings;
import eu.coatrack.admin.e2e.api.Tutorial;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static eu.coatrack.admin.e2e.CookieInjector.injectAuthenticationCookieToDriver;
import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getDashboard;
import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getDefaultPage;

public class PageFactory {

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

    public void loginWithCookieAndClickTutorial() {
        driver.get(getDefaultPage());
        driver.findElement(By.cssSelector("ul:nth-child(1) > li:nth-child(4) > a")).click();
        driver.get(getDashboard());
        driver.findElement(By.linkText("Tutorial")).click();
    }
}
