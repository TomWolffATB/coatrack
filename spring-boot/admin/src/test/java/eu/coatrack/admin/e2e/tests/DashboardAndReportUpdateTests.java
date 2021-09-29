package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.api.serviceProvider.AdminDashboard;
import eu.coatrack.admin.e2e.api.tools.GatewayRunner;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class DashboardAndReportUpdateTests extends AbstractTestSetup{

    private GatewayRunner gatewayRunner;

    @BeforeAll
    public void setupGatewayRunner() {
        //gatewayRunner = pageFactory.getGatewayRunner();
    }

    @Test
    public void serviceCallShouldUpdateDashboardStatsTest() {
        //Check Dashboard stats
        gatewayRunner.makeValidServiceCall();
        //Check if Dashboard stats changed
        gatewayRunner.stopGatewayAndCleanup();
    }

    @Test
    public void dashboardTest(){
        AdminDashboard adminDashboard = pageFactory.getAdminDashboard();
        System.out.println(adminDashboard.getCallsOfLoggedInUser());
    }

    @AfterAll
    public void tearDownGatewayRunner() {
        //gatewayRunner.stopGatewayAndCleanup();
    }

    //TODO Check dashboards and reports for Service Provider and Service Consumer.

    //TODO Apply the recommended terminology like 'Service Provider' instead of 'Admin'. Also check packages, classnames etc.
}
