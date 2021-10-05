package eu.coatrack.admin.selenium.api;

import eu.coatrack.admin.selenium.api.pages.serviceConsumer.ServiceConsumerApiKeys;
import eu.coatrack.admin.selenium.api.pages.serviceConsumer.ServiceConsumerDashboard;
import eu.coatrack.admin.selenium.api.pages.serviceConsumer.ServiceConsumerReports;
import eu.coatrack.admin.selenium.api.pages.serviceConsumer.ServiceConsumerServices;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.ServiceProviderDashboard;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.ServiceProviderReports;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.serviceOfferingsSetup.ServiceProviderApiKeys;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.serviceOfferingsSetup.ServiceProviderGateways;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.ServiceProviderTutorial;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.serviceOfferingsSetup.ServiceProviderServices;
import eu.coatrack.admin.selenium.api.tools.GatewayRunner;
import eu.coatrack.admin.selenium.api.tools.UrlReachabilityTools;
import org.openqa.selenium.WebDriver;
import static eu.coatrack.admin.selenium.configuration.CookieInjector.injectAuthenticationCookieToDriver;

//TODO Maybe it would be simpler to initialize every page at the beginning and make them public final fields.
// But this approach could create problems as GatewayRunner is executed immediately and would consume resources even for small tests.
public class PageFactory {

    private final WebDriver driver;

    public PageFactory(WebDriver driver) {
        this.driver = driver;
        injectAuthenticationCookieToDriver(driver);
    }

    public ServiceProviderTutorial getServiceProviderTutorial(){
        return new ServiceProviderTutorial(driver);
    }

    public ServiceProviderServices getServiceProviderServices(){
        return new ServiceProviderServices(driver);
    }

    public ServiceProviderGateways getServiceProviderGateways() {
        return new ServiceProviderGateways(driver);
    }

    public ServiceProviderApiKeys getServiceProviderApiKeys() {
        return new ServiceProviderApiKeys(driver);
    }

    public UrlReachabilityTools getPageChecker(){
        return new UrlReachabilityTools(driver);
    }

    public void closeDriver(){
        driver.close();
    }

    public ServiceConsumerServices getServiceConsumerServices() {
        return new ServiceConsumerServices(driver);
    }

    public ServiceConsumerApiKeys getServiceConsumerApiKeys() {
        return new ServiceConsumerApiKeys(driver);
    }

    public GatewayRunner getGatewayRunner() {
        return GatewayRunner.createAndRunGateway(driver);
    }

    public ServiceProviderDashboard getServiceProviderDashboard() {
        return new ServiceProviderDashboard(driver);
    }

    public ServiceConsumerDashboard getServiceConsumerDashboard() {
        return new ServiceConsumerDashboard(driver);
    }

    public ServiceProviderReports getServiceProviderReports() {
        return new ServiceProviderReports(driver);
    }

    public ServiceConsumerReports getServiceConsumerReports() {
        return new ServiceConsumerReports(driver);
    }
}
