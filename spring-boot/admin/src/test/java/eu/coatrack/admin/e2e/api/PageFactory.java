package eu.coatrack.admin.e2e.api;

import eu.coatrack.admin.e2e.api.serviceConsumer.ConsumerApiKeyList;
import eu.coatrack.admin.e2e.api.serviceConsumer.PublicServicesForConsumer;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminApiKeys;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceGateways;
import eu.coatrack.admin.e2e.api.serviceProvider.AdminTutorial;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceOfferings;
import org.openqa.selenium.WebDriver;
import static eu.coatrack.admin.e2e.configuration.CookieInjector.injectAuthenticationCookieToDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PageFactory {

    private final WebDriver driver;

    public PageFactory(WebDriver driver) {
        this.driver = driver;
        injectAuthenticationCookieToDriver(driver);
    }

    public AdminTutorial getTutorial(){
        return new AdminTutorial(driver);
    }

    public AdminServiceOfferings getServiceOfferings(){
        return new AdminServiceOfferings(driver);
    }

    public AdminServiceGateways getServiceGateways() {
        return new AdminServiceGateways(driver);
    }

    public AdminApiKeys getApiKeys() {
        return new AdminApiKeys(driver);
    }

    //TODO This does not belong to a factory. A new class like e.g. 'PageChecker' should be used instead.
    public void assertThatUrlIsReachable(String url){
        driver.get(url);
        assertThatCurrentPageHasNoError();
        assertEquals(url, driver.getCurrentUrl());
    }

    public void assertThatCurrentPageHasNoError(){
        if (driver.getPageSource().contains("Sorry, an error occurred."))
            fail();
    }

    public void closeDriver(){
        driver.close();
    }

    public PublicServicesForConsumer getConsumerServiceOfferings() {
        return new PublicServicesForConsumer(driver);
    }

    public ConsumerApiKeyList getConsumersApiKeyList() {
        return new ConsumerApiKeyList(driver);
    }
}
