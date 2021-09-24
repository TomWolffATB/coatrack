package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.api.PageFactory;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.firefox.FirefoxDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTestSetup {

    protected static PageFactory pageFactory = new PageFactory(new FirefoxDriver());

    static {
        pageFactory.getServiceOfferings().deleteAllServices();
        pageFactory.getServiceGateways().deleteAllGateways();
        pageFactory.getApiKeys().deleteAllApiKeys();
    }

}
