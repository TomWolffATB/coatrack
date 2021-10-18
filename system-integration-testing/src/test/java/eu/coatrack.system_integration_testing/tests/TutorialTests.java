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

import eu.coatrack.system_integration_testing.api.pages.serviceProvider.ItemDetails;
import org.junit.jupiter.api.Test;

import static eu.coatrack.system_integration_testing.api.PageFactory.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TutorialTests {

    @Test
    public void serviceProviderTutorialTest() {
        ItemDetails itemDetails = serviceProviderTutorial.createItemsViaTutorial();

        boolean wasApiKeyCreated = serviceProviderApiKeys.isApiKeyWithinList(itemDetails.apiKeyValue);
        assertTrue(wasApiKeyCreated);

        boolean wasServiceCreated = serviceProviderServices.isServiceWithinList(itemDetails.serviceName);
        assertTrue(wasServiceCreated);

        boolean wasGatewayCreated = serviceProviderGateways.isGatewayWithinList(itemDetails.gatewayName);
        assertTrue(wasGatewayCreated);

        serviceProviderApiKeys.deleteApiKey(itemDetails.apiKeyValue);
        serviceProviderServices.deleteService(itemDetails.serviceName);
        serviceProviderGateways.deleteGateway(itemDetails.gatewayName);
    }

    @Test
    public void serviceConsumerTutorialTest() {
        String exampleServiceAccessUrl = serviceConsumerTutorial.doTutorialAndReturnAccessUrlOfExampleService();

        boolean isReachable = serviceConsumerTutorial.isExampleServiceReachableWithAccessUrl(exampleServiceAccessUrl);
        assertTrue(isReachable);

        serviceConsumerTutorial.deleteApiKeyFromExampleServiceAccessUrl(exampleServiceAccessUrl);
    }

}
