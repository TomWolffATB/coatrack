package eu.coatrack.admin.e2e.tests.itemListsButtonsTests;

import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceGateways;
import eu.coatrack.admin.e2e.tests.AbstractTestSetup;
import org.junit.jupiter.api.*;

public class GatewaysListButtonsTests extends AbstractTestSetup {

    private AdminServiceGateways adminServiceGateways;
    private String gatewayName;

    @BeforeAll
    public void setupGateway() {
        adminServiceGateways = pageFactory.getServiceGateways();
        gatewayName = adminServiceGateways.createGateway();
    }

    @Test
    public void clickingFirstEditButtonShouldNotCauseErrorPage(){
        adminServiceGateways.clickOnDetailsButtonOfGateway(gatewayName);
    }

    @Test
    public void clickingDetailsButtonShouldNotCauseErrorPage(){
        adminServiceGateways.clickOnEditButtonOfGateway(gatewayName);
    }

    @AfterAll
    public void assertHavingNoErrorAndCleanup(){
        pageFactory.getPageChecker().assertThatCurrentPageHasNoError();
        adminServiceGateways.deleteGateway(gatewayName);
    }

}
