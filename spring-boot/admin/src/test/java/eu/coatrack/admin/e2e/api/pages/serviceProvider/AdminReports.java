package eu.coatrack.admin.e2e.api.pages.serviceProvider;

import eu.coatrack.admin.e2e.api.pages.AbstractReportsTemplate;
import org.openqa.selenium.WebDriver;

import static eu.coatrack.admin.e2e.configuration.PageConfiguration.consumerReportsUrl;

public class AdminReports extends AbstractReportsTemplate {

    public AdminReports(WebDriver driver) {
        super(driver, consumerReportsUrl);
    }
}
