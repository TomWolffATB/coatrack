package eu.coatrack.admin.e2e.tests;

import org.junit.jupiter.api.Test;

import static eu.coatrack.admin.e2e.configuration.TestConfiguration.*;

public class PageReachabilityTests extends AbstractTestSetup{

    @Test
    public void testAdminDashboardReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(getAdminDashboardUrl());
    }

    @Test
    public void testAdminTutorialReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(getAdminTutorialUrl());
    }

    @Test
    public void testAdminServiceListReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(getAdminServiceListUrl());
    }

    @Test
    public void testAdminGatewayListReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(getAdminGatewayListUrl());
    }

    @Test
    public void testAdminApiKeyListReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(getAdminApiKeyListUrl());
    }

    @Test
    public void testAdminReportsReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(getAdminReportsUrl());
    }

    @Test
    public void testConsumerDashboardReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(getConsumerDashboardUrl());
    }

    @Test
    public void testConsumerTutorialReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(getConsumerTutorialUrl());
    }

    @Test
    public void testConsumerApiKeyListReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(getConsumerApiKeyListUrl());
    }

    @Test
    public void testConsumerServiceListReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(getConsumerServiceListUrl());
    }

    @Test
    public void testConsumerReportsReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(getConsumerReportsUrl());
    }

}
