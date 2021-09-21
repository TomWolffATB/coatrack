package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.ApiKeys;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.ServiceGateways;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.ServiceOfferings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreationTests extends AbstractTestSetup {

    @Test
    public void createAndDeleteServiceTest() {
        ServiceOfferings serviceOfferings = pageFactory.getServiceOfferings();
        String serviceName = serviceOfferings.createService();
        assertTrue(serviceOfferings.isServiceWithinList(serviceName));

        serviceOfferings.deleteService(serviceName);
        assertFalse(serviceOfferings.isServiceWithinList(serviceName));
    }

    @Test
    public void createAndDeleteGatewayTest() {
        ServiceGateways serviceGateways = pageFactory.getServiceGateways();
        String gatewayName = serviceGateways.createGateway();
        assertTrue(serviceGateways.isGatewayWithinList(gatewayName));

        serviceGateways.deleteGateway(gatewayName);
        assertFalse(serviceGateways.isGatewayWithinList(gatewayName));
    }

    @Test
    public void createAndDeleteApiKeyTest() {
        ApiKeys apiKeys = pageFactory.getApiKeys();
        String serviceName = pageFactory.getServiceOfferings().createService();
        String apiKeyValue = apiKeys.createApiKey(serviceName);
        assertTrue(apiKeys.isApiKeyWithinList(apiKeyValue));

        apiKeys.deleteApiKey(apiKeyValue);
        assertFalse(apiKeys.isApiKeyWithinList(apiKeyValue));
    }

    //TODO Also test the consumer features.

}
