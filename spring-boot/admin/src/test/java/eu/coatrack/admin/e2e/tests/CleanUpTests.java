package eu.coatrack.admin.e2e.tests;

import org.junit.jupiter.api.Test;

//TODO When the all test implementations are finished, these cleanups should be executed once at the beginning as usual methods.
public class CleanUpTests extends AbstractTestSetup {

    @Test
    public void cleanUpServices(){
        pageFactory.getServiceOfferings().deleteAllServices();
    }

    @Test
    public void cleanUpGateways(){
        pageFactory.getServiceGateways().deleteAllGateways();
    }

    @Test
    public void cleanUpApiKeys(){
        pageFactory.getApiKeys().deleteAllApiKeys();
    }

}
