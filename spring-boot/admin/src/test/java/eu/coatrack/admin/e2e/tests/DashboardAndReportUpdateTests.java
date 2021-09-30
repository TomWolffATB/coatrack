package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.api.pages.serviceConsumer.ConsumerDashboard;
import eu.coatrack.admin.e2e.api.pages.serviceProvider.AdminDashboard;
import eu.coatrack.admin.e2e.api.pages.serviceProvider.AdminReports;
import eu.coatrack.admin.e2e.api.tools.GatewayRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardAndReportUpdateTests extends AbstractTestSetup{

    private GatewayRunner gatewayRunner;
    private AdminDashboard adminDashboard;
    private ConsumerDashboard consumerDashboard;
    private int numberOfServicesCalled;

    @BeforeAll
    public void setupGatewayRunner() {
        gatewayRunner = pageFactory.getGatewayRunner();
        adminDashboard = pageFactory.getAdminDashboard();
        consumerDashboard = pageFactory.getConsumerDashboard();
        numberOfServicesCalled = consumerDashboard.getNumberOfServicesCalled();
    }

    @Test
    public void adminDashboardTotalApiCallsUpdateTest() {
        int totalApiCalls = adminDashboard.getTotalApiCalls();

        gatewayRunner.makeValidServiceCall();
        gatewayRunner.makeValidServiceCall();

        assertEquals(totalApiCalls + 2, adminDashboard.getTotalApiCalls());
    }

    @Test
    public void adminDashboardApiUsageTrendUpdateTest() {
        int apiUsageTrend = adminDashboard.getApiUsageTrend();

        gatewayRunner.makeValidServiceCall();
        gatewayRunner.makeValidServiceCall();

        assertEquals(apiUsageTrend + 2, adminDashboard.getApiUsageTrend());
    }

    @Test
    public void adminDashboardCallsOfLoggedInUserUpdateTest() {
        int callsOfLoggedInUser = adminDashboard.getCallsOfLoggedInUser();

        gatewayRunner.makeValidServiceCall();
        gatewayRunner.makeValidServiceCall();

        assertEquals(callsOfLoggedInUser + 2, adminDashboard.getCallsOfLoggedInUser());
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
    public void adminReportUpdateTest() {
        AdminReports adminReports = pageFactory.getAdminReports();
        int calls = adminReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName);

        //TODO Maybe all fields should be saved at the beginning, then two service call are performed once, and after
        // that all the tests are run. This requires only two service call in total.
        gatewayRunner.makeValidServiceCall();
        gatewayRunner.makeValidServiceCall();

        assertEquals(calls + 2, adminReports.getNumberOfServiceCalls(gatewayRunner.getItemDetails().serviceName));
    }

    @AfterAll
    public void tearDownGatewayRunner() {
        gatewayRunner.stopGatewayAndCleanup();
    }

    //TODO Error call tests are still missing. Are there other numbers not tested yet? usageTrend and totalApiCall should not be updated when error call is made.

    //TODO Check dashboards and reports for Service Provider and Service Consumer.

    //TODO Apply the recommended terminology like 'Service Provider' instead of 'Admin'. Also check packages, classnames etc.
}
