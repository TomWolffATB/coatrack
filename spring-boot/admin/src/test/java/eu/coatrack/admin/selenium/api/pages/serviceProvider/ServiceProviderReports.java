package eu.coatrack.admin.selenium.api.pages.serviceProvider;

import eu.coatrack.admin.selenium.api.pages.AbstractReportsTemplate;

import static eu.coatrack.admin.selenium.configuration.PageConfiguration.serviceConsumerReportsUrl;

public class ServiceProviderReports extends AbstractReportsTemplate {

    public ServiceProviderReports() {
        super(serviceConsumerReportsUrl);
    }
}
