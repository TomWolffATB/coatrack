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
import org.openqa.selenium.firefox.FirefoxDriver;

import static eu.coatrack.admin.selenium.configuration.CookieInjector.injectAuthenticationCookieToDriver;

//TODO Maybe it would be simpler to initialize every page at the beginning and make them public final fields.
// But this approach could create problems as GatewayRunner is executed immediately and would consume resources even for small tests.
public class PageFactory {

    private static final WebDriver driver = new FirefoxDriver();

    static {
        injectAuthenticationCookieToDriver(driver);
    }

    public static final UrlReachabilityTools        urlReachabilityTools        = new UrlReachabilityTools(driver);
    public static final GatewayRunner               gatewayRunner               = new GatewayRunner(driver);

    public static final ServiceProviderDashboard    serviceProviderDashboard    = new ServiceProviderDashboard(driver);
    public static final ServiceProviderTutorial     serviceProviderTutorial     = new ServiceProviderTutorial(driver);
    public static final ServiceProviderServices     serviceProviderServices     = new ServiceProviderServices(driver);
    public static final ServiceProviderGateways     serviceProviderGateways     = new ServiceProviderGateways(driver);
    public static final ServiceProviderApiKeys      serviceProviderApiKeys      = new ServiceProviderApiKeys(driver);
    public static final ServiceProviderReports      serviceProviderReports      = new ServiceProviderReports(driver);

    public static final ServiceConsumerServices     serviceConsumerServices     = new ServiceConsumerServices(driver);
    public static final ServiceConsumerApiKeys      serviceConsumerApiKeys      = new ServiceConsumerApiKeys(driver);
    public static final ServiceConsumerDashboard    serviceConsumerDashboard    = new ServiceConsumerDashboard(driver);
    public static final ServiceConsumerReports      serviceConsumerReports      = new ServiceConsumerReports(driver);

    static {
        serviceProviderServices.deleteAllServices();
        serviceProviderGateways.deleteAllGateways();
        serviceProviderApiKeys.deleteAllApiKeys();
    }
}
