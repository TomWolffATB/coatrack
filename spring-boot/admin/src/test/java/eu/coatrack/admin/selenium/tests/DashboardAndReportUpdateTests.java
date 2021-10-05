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

    private GatewayRunner gatewayRunner;
    private ServiceProviderDashboard serviceProviderDashboard;
    private ServiceConsumerDashboard consumerDashboard;
    private int numberOfServicesCalled;

    @BeforeAll
    public void setupGatewayRunner() {
        gatewayRunner = pageFactory.getGatewayRunner();
        serviceProviderDashboard = pageFactory.getServiceProviderDashboard();
        consumerDashboard = pageFactory.getServiceConsumerDashboard();
        numberOfServicesCalled = consumerDashboard.getNumberOfServicesCalled();
    }

    @Test
    public void serviceProviderDashboardTotalApiCallsUpdateTest() {
        int totalApiCalls = serviceProviderDashboard.getTotalApiCalls();

        gatewayRunner.makeValidServiceCall();
        gatewayRunner.makeValidServiceCall();

        assertEquals(totalApiCalls + 2, serviceProviderDashboard.getTotalApiCalls());
    }

    @Test
    public void serviceProviderDashboardApiUsageTrendUpdateTest() {
        int apiUsageTrend = serviceProviderDashboard.getApiUsageTrend();

        gatewayRunner.makeValidServiceCall();
        gatewayRunner.makeValidServiceCall();

        assertEquals(apiUsageTrend + 2, serviceProviderDashboard.getApiUsageTrend());
    }

    @Test
    public void serviceProviderDashboardCallsOfLoggedInUserUpdateTest() {
        int callsOfLoggedInUser = serviceProviderDashboard.getCallsOfLoggedInUser();

        gatewayRunner.makeValidServiceCall();
        gatewayRunner.makeValidServiceCall();

        assertEquals(callsOfLoggedInUser + 2, serviceProviderDashboard.getCallsOfLoggedInUser());
    }

    @Test
    public void consumerDashboardTotalApiCallsUpdateTest() {
        int totalApiCalls = consumerDashboard.getTotalApiCalls();

        gatewayRunner.makeValidServiceCall();
        gatewayRunner.makeValidServiceCall();

        assertEquals(totalApiCalls + 2, consumerDashboard.getTotalApiCalls());
    }

    @Test
    public void consumerDashboardNumberOfServicesCalledUpdateTest() {
        gatewayRunner.makeValidServiceCall();
        gatewayRunner.makeValidServiceCall();

        assertEquals(numberOfServicesCalled + 1, consumerDashboard.getNumberOfServicesCalled());
    }

    @Test
    public void serviceProviderReportsUpdateTest() {
        ServiceProviderReports serviceProviderReports = pageFactory.getServiceProviderReports();
        int calls = serviceProviderReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName);

        //TODO Maybe all fields should be saved at the beginning, then two service call are performed once, and after
        // that all the tests are run. This requires only two service call in total.
        gatewayRunner.makeValidServiceCall();
        gatewayRunner.makeValidServiceCall();

        assertEquals(calls + 2, serviceProviderReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName));
    }

    @Test
    public void consumerReportsUpdateTest() {
        ServiceConsumerReports consumerReports = pageFactory.getServiceConsumerReports();
        int calls = consumerReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName);

        gatewayRunner.makeValidServiceCall();
        gatewayRunner.makeValidServiceCall();

        assertEquals(calls + 2, consumerReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName));
    }

    @AfterAll
    public void tearDownGatewayRunner() {
        gatewayRunner.stopGatewayAndCleanup();
    }

    //TODO Error call tests are still missing. Are there other numbers not tested yet? usageTrend and totalApiCall should not be updated when error call is made.

}
