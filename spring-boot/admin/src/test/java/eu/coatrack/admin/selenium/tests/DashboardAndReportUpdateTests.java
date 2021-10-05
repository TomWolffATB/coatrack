package eu.coatrack.admin.selenium.tests;

import eu.coatrack.admin.selenium.api.pages.serviceConsumer.ServiceConsumerDashboard;
import eu.coatrack.admin.selenium.api.pages.serviceConsumer.ServiceConsumerReports;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.ServiceProviderDashboard;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.ServiceProviderReports;
import eu.coatrack.admin.selenium.api.tools.GatewayRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardAndReportUpdateTests extends AbstractTestSetup{

    private final GatewayRunner gatewayRunner = pageFactory.getGatewayRunner();
    private final ServiceProviderDashboard serviceProviderDashboard = pageFactory.getServiceProviderDashboard();
    private final ServiceConsumerDashboard consumerDashboard = pageFactory.getServiceConsumerDashboard();

    private final int serviceProviderTotalApiCalls = serviceProviderDashboard.getTotalApiCalls();
    private final int serviceProviderApiUsageTrend = serviceProviderDashboard.getApiUsageTrend();
    private final int serviceProviderCallsOfLoggedInUser = serviceProviderDashboard.getCallsOfLoggedInUser();

    private final int serviceConsumerTotalApiCalls = consumerDashboard.getTotalApiCalls();
    private final int serviceConsumerNumberOfServicesCalled = consumerDashboard.getNumberOfServicesCalled();

    private final ServiceProviderReports serviceProviderReports = pageFactory.getServiceProviderReports();
    private final int serviceProviderReportedServiceCalls = serviceProviderReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName);

    private final ServiceConsumerReports serviceConsumerReports = pageFactory.getServiceConsumerReports();
    private final int serviceConsumerReportedServiceCalls = serviceConsumerReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName);

    @BeforeAll
    public void setupGatewayRunner() {
        gatewayRunner.makeValidServiceCall();
        gatewayRunner.makeValidServiceCall();
    }

    @Test
    public void serviceProviderDashboardTotalApiCallsUpdateTest() {
        assertEquals(serviceProviderTotalApiCalls + 2, serviceProviderDashboard.getTotalApiCalls());
    }

    @Test
    public void serviceProviderDashboardApiUsageTrendUpdateTest() {
        assertEquals(serviceProviderApiUsageTrend + 2, serviceProviderDashboard.getApiUsageTrend());
    }

    @Test
    public void serviceProviderDashboardCallsOfLoggedInUserUpdateTest() {
        assertEquals(serviceProviderCallsOfLoggedInUser + 2, serviceProviderDashboard.getCallsOfLoggedInUser());
    }

    @Test
    public void consumerDashboardTotalApiCallsUpdateTest() {
        assertEquals(serviceConsumerTotalApiCalls + 2, consumerDashboard.getTotalApiCalls());
    }

    @Test
    public void consumerDashboardNumberOfServicesCalledUpdateTest() {
        assertEquals(serviceConsumerNumberOfServicesCalled + 1, consumerDashboard.getNumberOfServicesCalled());
    }

    @Test
    public void serviceProviderReportsUpdateTest() {
        assertEquals(serviceProviderReportedServiceCalls + 2, serviceProviderReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName));
    }

    @Test
    public void consumerReportsUpdateTest() {
        assertEquals(serviceConsumerReportedServiceCalls + 2, serviceConsumerReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName));
    }

    @AfterAll
    public void tearDownGatewayRunner() {
        gatewayRunner.stopGatewayAndCleanup();
    }

    //TODO Error call tests are still missing. Are there other numbers not tested yet? usageTrend and totalApiCall should not be updated when error call is made.

}
