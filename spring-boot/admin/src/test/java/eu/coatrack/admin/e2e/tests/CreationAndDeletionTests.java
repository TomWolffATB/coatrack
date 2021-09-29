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

import eu.coatrack.admin.e2e.api.pages.serviceConsumer.ConsumerApiKeyList;
import eu.coatrack.admin.e2e.api.pages.serviceConsumer.ConsumerServiceOfferings;
import eu.coatrack.admin.e2e.api.pages.serviceProvider.serviceOfferingsSetup.AdminApiKeys;
import eu.coatrack.admin.e2e.api.pages.serviceProvider.serviceOfferingsSetup.AdminServiceGateways;
import eu.coatrack.admin.e2e.api.pages.serviceProvider.serviceOfferingsSetup.AdminServiceOfferings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreationAndDeletionTests extends AbstractTestSetup {

    @Test
    public void createAndDeleteServiceTest() {
        AdminServiceOfferings serviceOfferings = pageFactory.getServiceOfferings();
        String serviceName = serviceOfferings.createPublicService();
        assertTrue(serviceOfferings.isServiceWithinList(serviceName));

        serviceOfferings.deleteService(serviceName);
        assertFalse(serviceOfferings.isServiceWithinList(serviceName));
    }

    @Test
    public void createAndDeleteGatewayTest() {
        AdminServiceGateways serviceGateways = pageFactory.getServiceGateways();
        String gatewayName = serviceGateways.createGateway();
        assertTrue(serviceGateways.isGatewayWithinList(gatewayName));

        serviceGateways.deleteGateway(gatewayName);
        assertFalse(serviceGateways.isGatewayWithinList(gatewayName));
    }

    @Test
    public void createAndDeleteApiKeyTest() {
        AdminServiceOfferings adminServiceOfferings = pageFactory.getServiceOfferings();
        String serviceName = adminServiceOfferings.createPublicService();
        AdminApiKeys apiKeys = pageFactory.getApiKeys();
        String apiKeyValue = apiKeys.createApiKey(serviceName);
        assertTrue(apiKeys.isApiKeyWithinList(apiKeyValue));

        apiKeys.deleteApiKey(apiKeyValue);
        adminServiceOfferings.deleteService(serviceName);
        assertFalse(apiKeys.isApiKeyWithinList(apiKeyValue));
    }

    @Test
    public void createAndDeleteConsumerApiKeyForPublicServiceTest() {
        AdminServiceOfferings adminServiceOfferings = pageFactory.getServiceOfferings();
        String serviceName = adminServiceOfferings.createPublicService();

        ConsumerServiceOfferings consumerServiceOfferings = pageFactory.getConsumerServiceOfferings();
        String apiKeyValue = consumerServiceOfferings.createApiKeyFromPublicService(serviceName);

        ConsumerApiKeyList consumerApiKeyList = pageFactory.getConsumersApiKeyList();
        assertTrue(consumerApiKeyList.isApiKeyWithinList(apiKeyValue));

        //TODO Uncomment this line when #56 is solved and merged into this branch.
        //consumerApiKeyList.deletePublicApiKey(apiKeyValue);
        pageFactory.getApiKeys().deleteApiKey(apiKeyValue); //To deleted when #56 PR is merged to test the deletion feature.
        assertFalse(consumerApiKeyList.isApiKeyWithinList(apiKeyValue));

        adminServiceOfferings.deleteService(serviceName);
    }
}
