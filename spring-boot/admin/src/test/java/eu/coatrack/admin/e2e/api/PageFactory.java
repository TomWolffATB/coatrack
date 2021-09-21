package eu.coatrack.admin.e2e.api;

import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.ApiKeys;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.ServiceGateways;
import eu.coatrack.admin.e2e.api.serviceProvider.Tutorial;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.ServiceOfferings;
import org.openqa.selenium.WebDriver;

import static eu.coatrack.admin.e2e.configuration.CookieInjector.injectAuthenticationCookieToDriver;

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

    public ServiceGateways getServiceGateways() {
        return new ServiceGateways(driver);
    }

    public ApiKeys getApiKeys() {
        return new ApiKeys(driver);
    }

    public void close(){
        driver.close();
    }
}
