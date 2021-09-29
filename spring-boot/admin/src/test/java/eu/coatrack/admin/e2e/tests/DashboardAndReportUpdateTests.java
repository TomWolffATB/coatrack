package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.api.tools.GatewayRunner;
import org.junit.jupiter.api.Test;

public class DashboardAndReportUpdateTests extends AbstractTestSetup{

    @Test
    public void serviceCallShouldUpdateDashboardStatsTest() {
        GatewayRunner gatewayRunner = pageFactory.getGatewayRunner();
        //Check Dashboard stats
        gatewayRunner.makeValidServiceCall();
        //Check if Dashboard stats changed
        gatewayRunner.stopGatewayAndCleanup();
    }

}
