package eu.coatrack.system_integration_testing.tests.itemListsButtonsTests;

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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static eu.coatrack.system_integration_testing.api.PageFactory.serviceProviderServices;
import static eu.coatrack.system_integration_testing.api.PageFactory.urlReachabilityTools;

public class ServiceListButtonsTests {

    private static String serviceName;

    @BeforeAll
    public static void setupService() {
        serviceName = serviceProviderServices.createPublicService();
    }

    @Test
    public void clickingFirstEditButtonShouldNotCauseErrorPage(){
        serviceProviderServices.clickOnFirstEditButtonOfService(serviceName);
    }

    @Test
    public void clickingDetailsButtonShouldNotCauseErrorPage(){
        serviceProviderServices.clickDetailsButtonOfService(serviceName);
    }

    @Test
    public void clickingSecondEditButtonShouldNotCauseErrorPage(){
        serviceProviderServices.clickOnSecondEditButtonOfService(serviceName);
    }

    @AfterAll
    public static void assertHavingNoErrorAndCleanup(){
        urlReachabilityTools.throwExceptionIfErrorPageWasReceived();
        serviceProviderServices.deleteService(serviceName);
    }

}
