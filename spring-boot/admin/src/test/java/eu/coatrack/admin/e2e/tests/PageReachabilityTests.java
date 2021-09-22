package eu.coatrack.admin.e2e.tests;

import org.junit.jupiter.api.Test;

import static eu.coatrack.admin.e2e.configuration.TestConfiguration.*;

public class PageReachabilityTests extends AbstractTestSetup{

    @Test
    public void testAdminDashboardReachability(){
        pageFactory.checkIfUrlIsReachable(getAdminDashboardUrl());
    }

    @Test
    public void testAdminTutorialReachability(){
        pageFactory.checkIfUrlIsReachable(getAdminTutorialUrl());
    }

    @Test
    public void testAdminServiceListReachability(){
        pageFactory.checkIfUrlIsReachable(getAdminServiceListUrl());
    }

    @Test
    public void testAdminGatewayListReachability(){
        pageFactory.checkIfUrlIsReachable(getAdminGatewayListUrl());
    }

    @Test
    public void testAdminApiKeyListReachability(){
        pageFactory.checkIfUrlIsReachable(getAdminApiKeyListUrl());
    }

    @Test
    public void testAdminReportsReachability(){
        pageFactory.checkIfUrlIsReachable(getAdminReportsUrl());
    }

    @Test
    public void testConsumerDashboardReachability(){
        pageFactory.checkIfUrlIsReachable(getConsumerDashboardUrl());
    }

    @Test
    public void testConsumerTutorialReachability(){
        pageFactory.checkIfUrlIsReachable(getConsumerTutorialUrl());
    }

    @Test
    public void testConsumerApiKeyListReachability(){
        pageFactory.checkIfUrlIsReachable(getConsumerApiKeyListUrl());
    }

    @Test
    public void testConsumerServiceListReachability(){
        pageFactory.checkIfUrlIsReachable(getConsumerServiceListUrl());
    }

    @Test
    public void testConsumerReportsReachability(){
        pageFactory.checkIfUrlIsReachable(getConsumerReportsUrl());
    }

    //TODO Also implement reachability test for all the residual pages like API key details etc.

}
