package eu.coatrack.admin.selenium.api;

import eu.coatrack.admin.selenium.api.pages.LoginPage;
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

public class PageFactory {

    static final WebDriver driver = new FirefoxDriver();

    public static final UrlReachabilityTools        urlReachabilityTools        = new UrlReachabilityTools();
    public static final GatewayRunner               gatewayRunner               = new GatewayRunner();

    public static final LoginPage                   loginPage                   = new LoginPage();

    public static final ServiceProviderDashboard    serviceProviderDashboard    = new ServiceProviderDashboard();
    public static final ServiceProviderTutorial     serviceProviderTutorial     = new ServiceProviderTutorial();
    public static final ServiceProviderServices     serviceProviderServices     = new ServiceProviderServices();
    public static final ServiceProviderGateways     serviceProviderGateways     = new ServiceProviderGateways();
    public static final ServiceProviderApiKeys      serviceProviderApiKeys      = new ServiceProviderApiKeys();
    public static final ServiceProviderReports      serviceProviderReports      = new ServiceProviderReports();

    public static final ServiceConsumerServices     serviceConsumerServices     = new ServiceConsumerServices();
    public static final ServiceConsumerApiKeys      serviceConsumerApiKeys      = new ServiceConsumerApiKeys();
    public static final ServiceConsumerDashboard    serviceConsumerDashboard    = new ServiceConsumerDashboard();
    public static final ServiceConsumerReports      serviceConsumerReports      = new ServiceConsumerReports();

    static {
        injectAuthenticationCookieToDriver(driver);
        serviceProviderServices.deleteAllServices();
        serviceProviderGateways.deleteAllGateways();
        serviceProviderApiKeys.deleteAllApiKeys();
    }
}
