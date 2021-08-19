package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.AbstractTestSetup;
import eu.coatrack.admin.e2e.api.Tutorial;
import org.junit.jupiter.api.Test;

public class AdminTests extends AbstractTestSetup {

    @Test
    public void tutorialTest(){
        Tutorial tutorial = pageProvider.getTutorial().createItemsViaTutorial();

        System.out.println("Gateway Download Link: " + tutorial.getGatewayDownloadLink());
        System.out.println("API Key Value: " + tutorial.getApiKeyValue());
        System.out.println("Service Name: " + tutorial.getServiceName());

        //Download file https://www.baeldung.com/java-download-file

        //Run gateway jar

        //Test if API key reaches bing.com

    }


}
