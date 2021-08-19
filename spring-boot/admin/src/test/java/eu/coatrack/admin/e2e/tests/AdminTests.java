package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.AbstractTestSetup;
import eu.coatrack.admin.e2e.api.Dashboard;
import org.junit.jupiter.api.Test;

public class AdminTests extends AbstractTestSetup {

    @Test
    public void tutorialTest(){
        Dashboard dashboard = pageProvider.getDashboard().createItemsViaTutorial();

        System.out.println("Gateway Download Link: " + dashboard.getGatewayDownloadLink());
        System.out.println("API Key Value: " + dashboard.getApiKeyValue());
        System.out.println("Service Name: " + dashboard.getServiceName());

        //Download file https://www.baeldung.com/java-download-file


        //Run gateway jar

        //Test if API key reaches bing.com

    }


}
