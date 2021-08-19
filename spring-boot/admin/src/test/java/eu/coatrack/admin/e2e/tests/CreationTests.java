package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.AbstractTestSetup;
import org.junit.jupiter.api.Test;

public class CreationTests extends AbstractTestSetup {

    @Test
    public void createServiceTest() {
        pageProvider.getServiceOfferings().createService();
    }

}
