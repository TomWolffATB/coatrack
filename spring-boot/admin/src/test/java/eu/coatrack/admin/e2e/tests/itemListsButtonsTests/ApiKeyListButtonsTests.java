package eu.coatrack.admin.e2e.tests.itemListsButtonsTests;

import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminApiKeys;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceOfferings;
import eu.coatrack.admin.e2e.tests.AbstractTestSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApiKeyListButtonsTests extends AbstractTestSetup {

    private AdminApiKeys adminAPiKeys;
    private String apiKeyValue;
    private AdminServiceOfferings adminServiceOfferings;
    private String serviceName;

    //TODO This kind of creation/deletion should also be applied to the public service API key creation because the service created is not deleted at the end.
    @BeforeEach
    public void setupApiKey() {
        adminAPiKeys = pageFactory.getApiKeys();
        adminServiceOfferings = pageFactory.getServiceOfferings();
        serviceName = adminServiceOfferings.createPublicService();
        apiKeyValue = adminAPiKeys.createApiKey(serviceName);
    }

    @Test
    public void clickingOnCalendarButtonShouldNotCauseErrorPage(){
        adminAPiKeys.clickOnCalenderButtonOfApiKey(apiKeyValue);
    }

    @Test
    public void clickingDetailsButtonShouldNotCauseErrorPage(){
        adminAPiKeys.clickOnDetailsButtonOfApiKey(apiKeyValue);
    }

    @Test
    public void clickingEditButtonShouldNotCauseErrorPage(){
        adminAPiKeys.clickOnEditButtonOfApiKey(apiKeyValue);
    }

    @AfterEach
    public void assertHavingNoErrorAndCleanup(){
        pageFactory.getPageChecker().assertThatCurrentPageHasNoError();
        adminAPiKeys.deleteApiKey(apiKeyValue);
        adminServiceOfferings.deleteService(serviceName);
    }

    //TODO These kind of tests should also be implemented for gateways and API keys.

}
