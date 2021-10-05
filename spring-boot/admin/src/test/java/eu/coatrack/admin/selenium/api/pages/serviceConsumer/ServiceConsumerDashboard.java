package eu.coatrack.admin.selenium.api.pages.serviceConsumer;

import eu.coatrack.admin.selenium.api.pages.AbstractDashboardTemplate;
import org.openqa.selenium.WebDriver;

import static eu.coatrack.admin.selenium.configuration.PageConfiguration.serviceConsumerDashboardUrl;

public class ServiceConsumerDashboard extends AbstractDashboardTemplate {

    public ServiceConsumerDashboard(WebDriver driver) {
        super(driver, serviceConsumerDashboardUrl);
    }

    public int getNumberOfServicesCalled(){
        return getIntegerValueOfElementWithId("servicesCalled");
    }

}
