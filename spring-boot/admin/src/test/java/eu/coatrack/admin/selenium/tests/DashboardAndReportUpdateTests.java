package eu.coatrack.admin.selenium.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static eu.coatrack.admin.selenium.api.PageFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardAndReportUpdateTests {

    private final int serviceProviderTotalApiCalls = serviceProviderDashboard.getTotalApiCalls();
    private final int serviceProviderApiUsageTrend = serviceProviderDashboard.getApiUsageTrend();
    private final int serviceProviderCallsOfLoggedInUser = serviceProviderDashboard.getCallsOfLoggedInUser();

    private final int serviceConsumerTotalApiCalls = serviceConsumerDashboard.getTotalApiCalls();
    private final int serviceConsumerNumberOfServicesCalled = serviceConsumerDashboard.getNumberOfServicesCalled();

    private final int serviceProviderReportedServiceCalls = serviceProviderReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName);
    private final int serviceConsumerReportedServiceCalls = serviceConsumerReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName);

    public DashboardAndReportUpdateTests(){
        gatewayRunner.makeValidServiceCall();
        gatewayRunner.makeValidServiceCall();
    }

    @BeforeAll
    public static void setupGatewayRunner() {
        gatewayRunner.executeRunner();
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
        assertEquals(serviceConsumerTotalApiCalls + 2, serviceConsumerDashboard.getTotalApiCalls());
    }

    //TODO Test is broken, but why?
    @Test
    public void consumerDashboardNumberOfServicesCalledUpdateTest() {
        assertEquals(serviceConsumerNumberOfServicesCalled + 1, serviceConsumerDashboard.getNumberOfServicesCalled());
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
    public static void tearDownGatewayRunner() {
        gatewayRunner.stopGatewayAndCleanup();
    }

    //TODO Error call tests are still missing. Are there other numbers not tested yet? usageTrend and totalApiCall should not be updated when error call is made.

}
