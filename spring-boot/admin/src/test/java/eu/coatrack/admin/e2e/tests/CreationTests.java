package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.AbstractTestSetup;
import eu.coatrack.admin.e2e.api.ServiceOfferingsSetup.ServiceOfferings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreationTests extends AbstractTestSetup {

    @Test
    public void createServiceTest() {
        ServiceOfferings serviceOfferings = pageFactory.getServiceOfferings();
        String serviceName = serviceOfferings.createService();
        assertTrue(serviceOfferings.isServiceWithinList(serviceName));

        serviceOfferings.deleteService(serviceName);
        assertFalse(serviceOfferings.isServiceWithinList(serviceName));
    }

    //TODO Analog tests for proxies and API keys. Also test the consumer features.

}
