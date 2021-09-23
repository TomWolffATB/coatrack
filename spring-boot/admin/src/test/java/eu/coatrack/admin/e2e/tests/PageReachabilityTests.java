package eu.coatrack.admin.e2e.tests;

import org.junit.jupiter.api.Test;

import static eu.coatrack.admin.e2e.configuration.TestConfiguration.*;

public class PageReachabilityTests extends AbstractTestSetup{

    @Test
    public void testAdminDashboardReachability(){
        pageFactory.assertThatUrlIsReachable(getAdminDashboardUrl());
    }

    @Test
    public void testAdminTutorialReachability(){
        pageFactory.assertThatUrlIsReachable(getAdminTutorialUrl());
    }

    @Test
    public void testAdminServiceListReachability(){
        pageFactory.assertThatUrlIsReachable(getAdminServiceListUrl());
    }

    @Test
    public void testAdminGatewayListReachability(){
        pageFactory.assertThatUrlIsReachable(getAdminGatewayListUrl());
    }

    @Test
    public void testAdminApiKeyListReachability(){
        pageFactory.assertThatUrlIsReachable(getAdminApiKeyListUrl());
    }

    @Test
    public void testAdminReportsReachability(){
        pageFactory.assertThatUrlIsReachable(getAdminReportsUrl());
    }

    @Test
    public void testConsumerDashboardReachability(){
        pageFactory.assertThatUrlIsReachable(getConsumerDashboardUrl());
    }

    @Test
    public void testConsumerTutorialReachability(){
        pageFactory.assertThatUrlIsReachable(getConsumerTutorialUrl());
    }

    @Test
    public void testConsumerApiKeyListReachability(){
        pageFactory.assertThatUrlIsReachable(getConsumerApiKeyListUrl());
    }

    @Test
    public void testConsumerServiceListReachability(){
        pageFactory.assertThatUrlIsReachable(getConsumerServiceListUrl());
    }

    @Test
    public void testConsumerReportsReachability(){
        pageFactory.assertThatUrlIsReachable(getConsumerReportsUrl());
    }

}
