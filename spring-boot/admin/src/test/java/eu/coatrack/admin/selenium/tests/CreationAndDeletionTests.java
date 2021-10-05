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

import eu.coatrack.admin.selenium.api.pages.serviceConsumer.ServiceConsumerApiKeys;
import eu.coatrack.admin.selenium.api.pages.serviceConsumer.ServiceConsumerServices;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.serviceOfferingsSetup.ServiceProviderApiKeys;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.serviceOfferingsSetup.ServiceProviderGateways;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.serviceOfferingsSetup.ServiceProviderServices;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreationAndDeletionTests extends AbstractTestSetup {

    @Test
    public void createAndDeleteServiceTest() {
        ServiceProviderServices serviceOfferings = pageFactory.getServiceProviderServices();
        String serviceName = serviceOfferings.createPublicService();
        assertTrue(serviceOfferings.isServiceWithinList(serviceName));

        serviceOfferings.deleteService(serviceName);
        assertFalse(serviceOfferings.isServiceWithinList(serviceName));
    }

    @Test
    public void createAndDeleteGatewayTest() {
        ServiceProviderGateways serviceGateways = pageFactory.getServiceProviderGateways();
        String gatewayName = serviceGateways.createGateway();
        assertTrue(serviceGateways.isGatewayWithinList(gatewayName));

        serviceGateways.deleteGateway(gatewayName);
        assertFalse(serviceGateways.isGatewayWithinList(gatewayName));
    }

    @Test
    public void createAndDeleteApiKeyTest() {
        ServiceProviderServices adminServiceOfferings = pageFactory.getServiceProviderServices();
        String serviceName = adminServiceOfferings.createPublicService();
        ServiceProviderApiKeys apiKeys = pageFactory.getServiceProviderApiKeys();
        String apiKeyValue = apiKeys.createApiKey(serviceName);
        assertTrue(apiKeys.isApiKeyWithinList(apiKeyValue));

        apiKeys.deleteApiKey(apiKeyValue);
        adminServiceOfferings.deleteService(serviceName);
        assertFalse(apiKeys.isApiKeyWithinList(apiKeyValue));
    }

    @Test
    public void createAndDeleteConsumerApiKeyForPublicServiceTest() {
        ServiceProviderServices adminServiceOfferings = pageFactory.getServiceProviderServices();
        String serviceName = adminServiceOfferings.createPublicService();

        ServiceConsumerServices consumerServiceOfferings = pageFactory.getServiceConsumerServices();
        String apiKeyValue = consumerServiceOfferings.createApiKeyFromPublicService(serviceName);

        ServiceConsumerApiKeys consumerApiKeyList = pageFactory.getServiceConsumerApiKeys();
        assertTrue(consumerApiKeyList.isApiKeyWithinList(apiKeyValue));

        //TODO Uncomment this line when #56 is solved and merged into this branch.
        //consumerApiKeyList.deletePublicApiKey(apiKeyValue);
        pageFactory.getServiceProviderApiKeys().deleteApiKey(apiKeyValue); //To deleted when #56 PR is merged to test the deletion feature.
        assertFalse(consumerApiKeyList.isApiKeyWithinList(apiKeyValue));

        adminServiceOfferings.deleteService(serviceName);
    }
}
