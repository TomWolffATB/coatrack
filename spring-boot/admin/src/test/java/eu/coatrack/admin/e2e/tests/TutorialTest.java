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

import eu.coatrack.admin.e2e.api.pages.serviceProvider.ItemDto;
import eu.coatrack.admin.e2e.api.pages.serviceProvider.serviceOfferingsSetup.AdminApiKeys;
import eu.coatrack.admin.e2e.api.pages.serviceProvider.serviceOfferingsSetup.AdminServiceGateways;
import eu.coatrack.admin.e2e.api.pages.serviceProvider.serviceOfferingsSetup.AdminServiceOfferings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TutorialTest extends AbstractTestSetup {

    @Test
    public void tutorialTest() {
        ItemDto itemDto = pageFactory.getTutorial().createItemsViaTutorial();

        AdminApiKeys adminApiKeys = pageFactory.getApiKeys();
        boolean wasApiKeyCreated = adminApiKeys.isApiKeyWithinList(itemDto.apiKeyValue);
        assertTrue(wasApiKeyCreated);

        AdminServiceOfferings adminServiceOfferings = pageFactory.getServiceOfferings();
        boolean wasServiceCreated = adminServiceOfferings.isServiceWithinList(itemDto.serviceName);
        assertTrue(wasServiceCreated);

        AdminServiceGateways adminServiceGateways = pageFactory.getServiceGateways();
        boolean wasGatewayCreated = adminServiceGateways.isGatewayWithinList(itemDto.gatewayName);
        assertTrue(wasGatewayCreated);

        adminApiKeys.deleteApiKey(itemDto.apiKeyValue);
        adminServiceOfferings.deleteService(itemDto.serviceName);
        adminServiceGateways.deleteGateway(itemDto.gatewayName);
    }

}
