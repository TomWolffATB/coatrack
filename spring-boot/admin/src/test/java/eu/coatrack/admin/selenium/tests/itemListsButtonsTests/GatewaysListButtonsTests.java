package eu.coatrack.admin.selenium.tests.itemListsButtonsTests;

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

import eu.coatrack.admin.selenium.api.pages.serviceProvider.serviceOfferingsSetup.ServiceProviderGateways;
import eu.coatrack.admin.selenium.tests.AbstractTestSetup;
import org.junit.jupiter.api.*;

import static eu.coatrack.admin.selenium.api.PageFactory.serviceProviderGateways;
import static eu.coatrack.admin.selenium.api.PageFactory.urlReachabilityTools;

public class GatewaysListButtonsTests extends AbstractTestSetup {

    private String gatewayName;

    @BeforeAll
    public void setupGateway() {
        gatewayName = serviceProviderGateways.createGateway();
    }

    @Test
    public void clickingFirstEditButtonShouldNotCauseErrorPage(){
        serviceProviderGateways.clickOnDetailsButtonOfGateway(gatewayName);
        urlReachabilityTools.throwExceptionIfErrorPageWasReceived();
    }

    @Test
    public void clickingDetailsButtonShouldNotCauseErrorPage(){
        serviceProviderGateways.clickOnEditButtonOfGateway(gatewayName);
        urlReachabilityTools.throwExceptionIfErrorPageWasReceived();
    }

    @AfterAll
    public void assertHavingNoErrorAndCleanup(){
        serviceProviderGateways.deleteGateway(gatewayName);
    }

}
