package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.AbstractTestSetup;
import eu.coatrack.admin.e2e.api.ServiceOfferingsSetup.ServiceOfferings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreationTests extends AbstractTestSetup {

    @Test
    public void createServiceTest() {
        ServiceOfferings serviceOfferings = pageProvider.getServiceOfferings();
        String serviceName = serviceOfferings.createService();
        assertTrue(serviceOfferings.isServiceWithinList(serviceName));

        serviceOfferings.deleteService(serviceName);
        assertFalse(serviceOfferings.isServiceWithinList(serviceName));
    }

    @Test
    public void isPresenceOfServiceDetected(){
        assertTrue(pageProvider.getServiceOfferings().isServiceWithinList("Humidity by location"));
    }

    //TODO Analog tests for proxies and API keys. Also test the consumer features.

}
