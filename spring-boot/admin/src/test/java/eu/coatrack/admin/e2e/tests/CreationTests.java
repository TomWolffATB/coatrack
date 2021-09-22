package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminApiKeys;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceGateways;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceOfferings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreationTests extends AbstractTestSetup {

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

    //TODO Implement the same test procedure for consumers API keys.
    @Test
    public void createAndDeleteConsumerApiKeyForPublicServiceTest() {
        String serviceName = pageFactory.getServiceOfferings().createPublicService();
        String apiKeyValue = pageFactory.getConsumerServiceOfferings().createApiKeyFromPublicService(serviceName);

        System.out.println("Api key value: " + apiKeyValue);
        //Add assertion via isApiKeyWithinList
        //Delete Api key
        //Add assertion via !isApiKeyWithinList
    }

    //TODO After every test, all setup item shall be deleted. Check this.
}
