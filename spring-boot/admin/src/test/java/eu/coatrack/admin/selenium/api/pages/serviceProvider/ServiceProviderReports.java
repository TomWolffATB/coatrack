package eu.coatrack.admin.selenium.api.pages.serviceProvider;

import eu.coatrack.admin.selenium.api.pages.AbstractReportsTemplate;
import org.openqa.selenium.WebDriver;

import static eu.coatrack.admin.selenium.configuration.PageConfiguration.serviceConsumerReportsUrl;

public class ServiceProviderReports extends AbstractReportsTemplate {

    public ServiceProviderReports(WebDriver driver) {
        super(driver, serviceConsumerReportsUrl);
    }
}
