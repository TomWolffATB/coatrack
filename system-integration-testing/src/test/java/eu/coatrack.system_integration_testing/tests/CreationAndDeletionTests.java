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

import static eu.coatrack.system_integration_testing.api.PageFactory.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreationAndDeletionTests {

    @Test
    public void createAndDeleteServiceTest() {
        String serviceName = serviceProviderServices.createPublicService();
        assertTrue(serviceProviderServices.isServiceWithinList(serviceName));

        serviceProviderServices.deleteService(serviceName);
        assertFalse(serviceProviderServices.isServiceWithinList(serviceName));
    }

    @Test
    public void createAndDeleteGatewayTest() {
        String gatewayName = serviceProviderGateways.createGateway();
        assertTrue(serviceProviderGateways.isGatewayWithinList(gatewayName));

        serviceProviderGateways.deleteGateway(gatewayName);
        assertFalse(serviceProviderGateways.isGatewayWithinList(gatewayName));
    }

    @Test
    public void createAndDeleteApiKeyTest() {
        String serviceName = serviceProviderServices.createPublicService();
        String apiKeyValue = serviceProviderApiKeys.createApiKey(serviceName);
        assertTrue(serviceProviderApiKeys.isApiKeyWithinList(apiKeyValue));

        serviceProviderApiKeys.deleteApiKey(apiKeyValue);
        serviceProviderServices.deleteService(serviceName);
        assertFalse(serviceProviderApiKeys.isApiKeyWithinList(apiKeyValue));
    }

    //TODO Test is not working because the public services table is overfilled.
    // Uncomment it when #56 is merged and the table can be cleaned up.
    //@Test
    public void createAndDeleteConsumerApiKeyForPublicServiceTest() {
        String serviceName = serviceProviderServices.createPublicService();
        String apiKeyValue = serviceConsumerServices.createApiKeyFromPublicService(serviceName);

        assertTrue(serviceConsumerApiKeys.isApiKeyWithinList(apiKeyValue));

        //TODO Uncomment this line when #56 is solved and merged into this branch.
        //serviceConsumerApiKeyList.deletePublicApiKey(apiKeyValue);
        serviceProviderApiKeys.deleteApiKey(apiKeyValue); //To deleted when #56 PR is merged to test the deletion feature.
        assertFalse(serviceConsumerApiKeys.isApiKeyWithinList(apiKeyValue));

        serviceProviderServices.deleteService(serviceName);
    }
}
