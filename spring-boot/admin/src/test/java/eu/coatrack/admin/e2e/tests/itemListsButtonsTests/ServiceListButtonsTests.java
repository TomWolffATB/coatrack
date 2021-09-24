package eu.coatrack.admin.e2e.tests.itemListsButtonsTests;

import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceOfferings;
import eu.coatrack.admin.e2e.tests.AbstractTestSetup;
import org.junit.jupiter.api.*;

public class ServiceListButtonsTests extends AbstractTestSetup {

    private AdminServiceOfferings adminServiceOfferings;
    private String serviceName;

    @BeforeAll
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

    @AfterAll
    public void assertHavingNoErrorAndCleanup(){
        pageFactory.getPageChecker().assertThatCurrentPageHasNoError();
        adminServiceOfferings.deleteService(serviceName);
    }

}
