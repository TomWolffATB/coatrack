package eu.coatrack.admin.e2e.api;

import eu.coatrack.admin.e2e.api.serviceConsumer.ConsumerApiKeyList;
import eu.coatrack.admin.e2e.api.serviceConsumer.ConsumerDashboard;
import eu.coatrack.admin.e2e.api.serviceConsumer.ConsumerServiceOfferings;
import eu.coatrack.admin.e2e.api.serviceProvider.AdminDashboard;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminApiKeys;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceGateways;
import eu.coatrack.admin.e2e.api.serviceProvider.AdminTutorial;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceOfferings;
import eu.coatrack.admin.e2e.api.tools.GatewayRunner;
import eu.coatrack.admin.e2e.api.tools.UrlReachabilityTools;
import org.openqa.selenium.WebDriver;
import static eu.coatrack.admin.e2e.configuration.CookieInjector.injectAuthenticationCookieToDriver;

//TODO Maybe it would be simpler to initialize every page at the beginning and make them public final fields.
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

    public UrlReachabilityTools getPageChecker(){
        return new UrlReachabilityTools(driver);
    }

    public void closeDriver(){
        driver.close();
    }

    public ConsumerServiceOfferings getConsumerServiceOfferings() {
        return new ConsumerServiceOfferings(driver);
    }

    public ConsumerApiKeyList getConsumersApiKeyList() {
        return new ConsumerApiKeyList(driver);
    }

    public GatewayRunner getGatewayRunner() {
        return GatewayRunner.createAndRunGateway(driver);
    }

    public AdminDashboard getAdminDashboard() {
        return new AdminDashboard(driver);
    }

    public ConsumerDashboard getConsumerDashboard() {
        return new ConsumerDashboard(driver);
    }
}
