package eu.coatrack.admin.e2e.tests;

/*-
 * #%L
 * coatrack-admin
 * %%
 * Copyright (C) 2013 - 2021 Corizon | Institut für angewandte Systemtechnik Bremen GmbH (ATB)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.junit.jupiter.api.Test;

import static eu.coatrack.admin.e2e.configuration.PageConfiguration.*;

public class PageReachabilityTests extends AbstractTestSetup{

    @Test
    public void testAdminDashboardReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(adminDashboardUrl);
    }

    @Test
    public void testAdminTutorialReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(adminTutorialUrl);
    }

    @Test
    public void testAdminServiceListReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(adminServiceListUrl);
    }

    @Test
    public void testAdminGatewayListReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(adminGatewayListUrl);
    }

    @Test
    public void testAdminApiKeyListReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(adminApiKeyListUrl);
    }

    @Test
    public void testAdminReportsReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(adminReportsUrl);
    }

    @Test
    public void testConsumerDashboardReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(consumerDashboardUrl);
    }

    @Test
    public void testConsumerTutorialReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(consumerTutorialUrl);
    }

    @Test
    public void testConsumerApiKeyListReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(consumerApiKeyListUrl);
    }

    @Test
    public void testConsumerServiceListReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(consumerServiceListUrl);
    }

    @Test
    public void testConsumerReportsReachability(){
        pageFactory.getPageChecker().assertThatUrlIsReachable(consumerReportsUrl);
    }

}
