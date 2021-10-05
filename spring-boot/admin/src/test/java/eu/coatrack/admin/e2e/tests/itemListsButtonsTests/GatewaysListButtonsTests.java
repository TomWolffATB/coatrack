package eu.coatrack.admin.e2e.tests.itemListsButtonsTests;

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

import eu.coatrack.admin.e2e.api.pages.serviceProvider.serviceOfferingsSetup.AdminServiceGateways;
import eu.coatrack.admin.e2e.tests.AbstractTestSetup;
import org.junit.jupiter.api.*;

public class GatewaysListButtonsTests extends AbstractTestSetup {

    private AdminServiceGateways adminServiceGateways;
    private String gatewayName;

    @BeforeAll
    public void setupGateway() {
        adminServiceGateways = pageFactory.getServiceGateways();
        gatewayName = adminServiceGateways.createGateway();
    }

    @Test
    public void clickingFirstEditButtonShouldNotCauseErrorPage(){
        adminServiceGateways.clickOnDetailsButtonOfGateway(gatewayName);
    }

    @Test
    public void clickingDetailsButtonShouldNotCauseErrorPage(){
        adminServiceGateways.clickOnEditButtonOfGateway(gatewayName);
    }

    @AfterAll
    public void assertHavingNoErrorAndCleanup(){
        pageFactory.getPageChecker().throwExceptionIfErrorPageWasReceived();
        adminServiceGateways.deleteGateway(gatewayName);
    }

}
