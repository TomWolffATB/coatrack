package eu.coatrack.admin.selenium.api.pages.serviceConsumer;

import eu.coatrack.admin.selenium.api.pages.AbstractReportsTemplate;
import org.openqa.selenium.WebDriver;

import static eu.coatrack.admin.selenium.configuration.PageConfiguration.serviceConsumerReportsUrl;

public class ServiceConsumerReports extends AbstractReportsTemplate {

    public ServiceConsumerReports(WebDriver driver) {
        super(driver, serviceConsumerReportsUrl);
    }
}
