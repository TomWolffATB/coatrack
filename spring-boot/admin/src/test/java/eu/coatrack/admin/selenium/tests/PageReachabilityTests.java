package eu.coatrack.admin.selenium.tests;

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

import static eu.coatrack.admin.selenium.api.PageFactory.urlReachabilityTools;
import static eu.coatrack.admin.selenium.configuration.PageConfiguration.*;

public class PageReachabilityTests extends AbstractTestSetup{

    @Test
    public void testAdminDashboardReachability(){
        urlReachabilityTools.assertThatUrlIsReachable(serviceProviderDashboardUrl);
    }

    @Test
    public void testAdminTutorialReachability(){
        urlReachabilityTools.assertThatUrlIsReachable(serviceProviderTutorialUrl);
    }

    @Test
    public void testAdminServiceListReachability(){
        urlReachabilityTools.assertThatUrlIsReachable(serviceProviderServicesUrl);
    }

    @Test
    public void testAdminGatewayListReachability(){
        urlReachabilityTools.assertThatUrlIsReachable(serviceProviderGatewaysUrl);
    }

    @Test
    public void testAdminApiKeyListReachability(){
        urlReachabilityTools.assertThatUrlIsReachable(serviceProviderApiKeysUrl);
    }

    @Test
    public void testAdminReportsReachability(){
        urlReachabilityTools.assertThatUrlIsReachable(serviceProviderReportsUrl);
    }

    @Test
    public void testConsumerDashboardReachability(){
        urlReachabilityTools.assertThatUrlIsReachable(serviceConsumerDashboardUrl);
    }

    @Test
    public void testConsumerTutorialReachability(){
        urlReachabilityTools.assertThatUrlIsReachable(serviceConsumerTutorialUrl);
    }

    @Test
    public void testConsumerApiKeyListReachability(){
        urlReachabilityTools.assertThatUrlIsReachable(serviceConsumerApiKeyListUrl);
    }

    @Test
    public void testConsumerServiceListReachability(){
        urlReachabilityTools.assertThatUrlIsReachable(serviceConsumerServiceListUrl);
    }

    @Test
    public void testConsumerReportsReachability(){
        urlReachabilityTools.assertThatUrlIsReachable(serviceConsumerReportsUrl);
    }

}
