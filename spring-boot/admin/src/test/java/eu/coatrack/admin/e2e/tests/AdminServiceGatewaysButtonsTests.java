package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceGateways;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminServiceGatewaysButtonsTests extends AbstractTestSetup {

    private AdminServiceGateways adminServiceGateways;
    private String gatewayName;

    @BeforeEach
    public void setupService() {
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

    @AfterEach
    public void assertHavingNoErrorAndCleanup(){
        pageFactory.getPageChecker().assertThatCurrentPageHasNoError();
        adminServiceGateways.deleteGateway(gatewayName);
    }

}
