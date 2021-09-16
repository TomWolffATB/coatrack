package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.AbstractTestSetup;
import eu.coatrack.admin.e2e.api.Tutorial;
import org.junit.jupiter.api.Test;

import java.io.*;

import static eu.coatrack.admin.e2e.CookieInjector.authenticationCookie;
import static eu.coatrack.admin.e2e.CookieInjector.injectAuthenticationCookieToDriver;
import static org.junit.jupiter.api.Assertions.fail;

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
        Process pr2 = rt.exec("cmd /c start cmd /k java -jar ./test.jar");
        /** Approaches to get this working:
         1) pr2.send("STRG + C")
         2) Start commandline window -> after that send the second command to that process
         3) rt.exec("java -jar ./test.jar");
         */

        Thread.sleep(30000);

        //This is somehow not sufficient. This could help: https://stackoverflow.com/questions/19726804/how-to-kill-a-process-in-java-process-destroy
        pr2.destroyForcibly();
        /*
        Process pr3 = rt.exec("cmd /c taskkill /F /PID " + pr2.pid());

        BufferedReader reader2 = new BufferedReader(new InputStreamReader(pr3.getInputStream()));

        String line2 = reader2.readLine();
        while (line2 != null){
            System.out.println(line2);
            line2 = reader2.readLine();
        }
        */

        //Test if API key reaches bing.com

        //Cleanup
        File file = new File("./test.jar");
        if (file.exists())
            file.delete();

        if (file.exists())
            fail("Cleanup failed. File test.jar could not be deleted.");
    }

    @Test
    public void cleanUp(){
        pageFactory.getServiceOfferings().deleteAllServices();
        //TODO Assertion of service list to be empty is missing.

        //TODO Also implement cleanups for API Keys and Gateways with proper assertions.
    }

}
