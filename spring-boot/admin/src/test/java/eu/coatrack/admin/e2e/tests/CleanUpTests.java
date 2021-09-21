package eu.coatrack.admin.e2e.tests;

import org.junit.jupiter.api.Test;

public class CleanUpTests extends AbstractTestSetup {

    @Test
    public void cleanUpServices(){
        pageFactory.getServiceOfferings().deleteAllServices();
        //TODO This test always fails. -> Fix needed.
        //TODO Assertion of service list to be empty is missing.
        //TODO Also implement cleanups for API Keys and Gateways with proper assertions.
    }

    @Test
    public void cleanUpGateways(){
        pageFactory.getServiceGateways().deleteAllGateways();
    }

}
