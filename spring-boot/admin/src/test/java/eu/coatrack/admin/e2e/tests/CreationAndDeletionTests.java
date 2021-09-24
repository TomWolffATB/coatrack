package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.api.serviceConsumer.ConsumerApiKeyList;
import eu.coatrack.admin.e2e.api.serviceConsumer.ConsumerServiceOfferings;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminApiKeys;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceGateways;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceOfferings;
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
        AdminApiKeys apiKeys = pageFactory.getApiKeys();
        String serviceName = pageFactory.getServiceOfferings().createPublicService();
        String apiKeyValue = apiKeys.createApiKey(serviceName);
        assertTrue(apiKeys.isApiKeyWithinList(apiKeyValue));

        apiKeys.deleteApiKey(apiKeyValue);
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
