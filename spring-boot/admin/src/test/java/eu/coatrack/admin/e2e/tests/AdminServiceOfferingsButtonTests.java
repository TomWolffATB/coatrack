package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceOfferings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminServiceOfferingsButtonTests extends AbstractTestSetup {

    private AdminServiceOfferings adminServiceOfferings;
    private String serviceName;

    @BeforeEach
    public void setupService() {
        adminServiceOfferings = pageFactory.getServiceOfferings();
        serviceName = adminServiceOfferings.createPublicService();
    }

    @Test
    public void clickingFirstEditButtonShouldNotCauseErrorPage(){
        adminServiceOfferings.clickOnFirstEditButtonOfService(serviceName);
    }

    @Test
    public void clickingDetailsButtonShouldNotCauseErrorPage(){
        adminServiceOfferings.clickDetailsButtonOfService(serviceName);
    }

    @Test
    public void clickingSecondEditButtonShouldNotCauseErrorPage(){
        adminServiceOfferings.clickOnSecondEditButtonOfService(serviceName);
    }

    @AfterEach
    public void assertHavingNoErrorAndCleanup(){
        pageFactory.assertThatCurrentPageHasNoError();
        adminServiceOfferings.deleteService(serviceName);
    }

    //TODO These kind of tests should also be implemented for gateways and API keys.

}
