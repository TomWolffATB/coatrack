package eu.coatrack.system_integration_testing.tests;

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

import static eu.coatrack.system_integration_testing.api.PageFactory.urlReachabilityTools;
import static eu.coatrack.system_integration_testing.configuration.PageConfiguration.*;

public class PageReachabilityTests {

    @Test
    public void testAdminDashboardReachability(){
        urlReachabilityTools.throwExceptionIfUrlIsNotReachable(serviceProviderDashboardUrl);
    }

    @Test
    public void testAdminTutorialReachability(){
        urlReachabilityTools.throwExceptionIfUrlIsNotReachable(serviceProviderTutorialUrl);
    }

    @Test
    public void testAdminServiceListReachability(){
        urlReachabilityTools.throwExceptionIfUrlIsNotReachable(serviceProviderServicesUrl);
    }

    @Test
    public void testAdminGatewayListReachability(){
        urlReachabilityTools.throwExceptionIfUrlIsNotReachable(serviceProviderGatewaysUrl);
    }

    @Test
    public void testAdminApiKeyListReachability(){
        urlReachabilityTools.throwExceptionIfUrlIsNotReachable(serviceProviderApiKeysUrl);
    }

    @Test
    public void testAdminReportsReachability(){
        urlReachabilityTools.throwExceptionIfUrlIsNotReachable(serviceProviderReportsUrl);
    }

    @Test
    public void testConsumerDashboardReachability(){
        urlReachabilityTools.throwExceptionIfUrlIsNotReachable(serviceConsumerDashboardUrl);
    }

    @Test
    public void testConsumerTutorialReachability(){
        urlReachabilityTools.throwExceptionIfUrlIsNotReachable(serviceConsumerTutorialUrl);
    }

    @Test
    public void testConsumerApiKeyListReachability(){
        urlReachabilityTools.throwExceptionIfUrlIsNotReachable(serviceConsumerApiKeyListUrl);
    }

    @Test
    public void testConsumerServiceListReachability(){
        urlReachabilityTools.throwExceptionIfUrlIsNotReachable(serviceConsumerServiceListUrl);
    }

    @Test
    public void testConsumerReportsReachability(){
        urlReachabilityTools.throwExceptionIfUrlIsNotReachable(serviceConsumerReportsUrl);
    }

}
