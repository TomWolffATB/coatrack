package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.api.serviceProvider.ItemDto;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceGateways;
import org.junit.jupiter.api.Test;

import java.io.*;

import static eu.coatrack.admin.e2e.configuration.CookieInjector.sessionCookie;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.host;
import static org.junit.jupiter.api.Assertions.*;

public class TutorialTests extends AbstractTestSetup {

    @Test
    public void tutorialTest() throws IOException, InterruptedException {
        ItemDto itemDto = pageFactory.getTutorial().createItemsViaTutorial();

        //Download file
        File file = new File("test.jar");
        if (file.exists())
            file.delete();
        assertFalse(file.exists());

        Runtime rt = Runtime.getRuntime();
        String firstPartOfCommand = "cmd /c curl -v ";
        if (host.equals("localhost"))
            firstPartOfCommand += "-k ";
        String command = firstPartOfCommand + "--cookie \"SESSION=" + sessionCookie.getValue() + "\" --output ./test.jar " + itemDto.gatewayDownloadLink;
        Process pr = rt.exec(command);
        pr.waitFor();

        assertTrue(file.exists());
        file.delete();

        /*
        //Run gateway jar
        Process pr2 = rt.exec("cmd /c start cmd /k java -jar ./test.jar");
        // Approaches to get this working:
         1) pr2.send("STRG + C")
         2) Start commandline window -> after that send the second command to that process
         3) rt.exec("java -jar ./test.jar");


        sleepMillis(30000);

        //This is somehow not sufficient. This could help: https://stackoverflow.com/questions/19726804/how-to-kill-a-process-in-java-process-destroy
        pr2.destroyForcibly();

        Process pr3 = rt.exec("cmd /c taskkill /F /PID " + pr2.pid());

        BufferedReader reader2 = new BufferedReader(new InputStreamReader(pr3.getInputStream()));

        String line2 = reader2.readLine();
        while (line2 != null){
            System.out.println(line2);
            line2 = reader2.readLine();
        }
        */

        //Test if API key reaches example website

        AdminServiceGateways adminServiceGateways = pageFactory.getServiceGateways();
        String gatewayName = adminServiceGateways.getGatewayNameByIdentifier(itemDto.gatewayIdentifier);
        adminServiceGateways.deleteGateway(gatewayName);

        pageFactory.getApiKeys().deleteApiKey(itemDto.apiKeyValue);
        pageFactory.getServiceOfferings().deleteService(itemDto.serviceName);
    }

}
