package eu.coatrack.admin.e2e.api.pages.serviceConsumer;

import eu.coatrack.admin.e2e.api.pages.AbstractReportsTemplate;
import org.openqa.selenium.WebDriver;

import static eu.coatrack.admin.e2e.configuration.PageConfiguration.adminReportsUrl;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.consumerReportsUrl;

public class ConsumerReports extends AbstractReportsTemplate {

    public ConsumerReports(WebDriver driver) {
        super(driver, consumerReportsUrl);
    }
}
