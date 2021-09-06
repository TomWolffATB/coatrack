package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.AbstractTestSetup;
import eu.coatrack.admin.e2e.api.Tutorial;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static eu.coatrack.admin.e2e.CookieInjector.authenticationCookie;

public class AdminTests extends AbstractTestSetup {

    @Test
    public void tutorialTest() throws IOException, InterruptedException {
        Tutorial tutorial = pageFactory.getTutorial().createItemsViaTutorial();

        System.out.println("Gateway Download Link: " + tutorial.getGatewayDownloadLink());
        System.out.println("API Key Value: " + tutorial.getApiKeyValue());
        System.out.println("Service Name: " + tutorial.getServiceName());

        //Download file
        Runtime rt = Runtime.getRuntime();
        Process pr = rt.exec("cmd /c wsl curl -v --cookie \"SESSION=" + authenticationCookie.getValue() + "\" --output ./test.jar " + tutorial.getGatewayDownloadLink());
        pr.waitFor();

        //Run gateway jar
        Process pr2 = rt.exec("cmd /c java -jar ./test.jar");

        Thread.sleep(60000);
        pr2.destroy();

        //Test if API key reaches bing.com

        //Cleanup
        //TODO Delete gateway file after
    }

}
