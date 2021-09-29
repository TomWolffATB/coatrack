package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.api.tools.GatewayRunner;
import org.junit.jupiter.api.Test;

public class StatisticsTests extends AbstractTestSetup{

    @Test
    public void serviceCallShouldUpdateDashboardStatsTest() {
        GatewayRunner gatewayRunner = pageFactory.getGatewayRunner();
        //Check Dashboard stats
        gatewayRunner.makeValidCall();
        //Check if Dashboard stats changed
        gatewayRunner.stopGatewayAndCleanup();
    }

}
