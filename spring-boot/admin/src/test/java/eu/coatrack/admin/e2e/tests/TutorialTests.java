package eu.coatrack.admin.e2e.tests;

import eu.coatrack.admin.e2e.api.serviceProvider.ItemDto;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceGateways;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import static eu.coatrack.admin.e2e.configuration.CookieInjector.sessionCookie;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.host;
import static org.junit.jupiter.api.Assertions.*;

public class TutorialTests extends AbstractTestSetup {

    private static final Logger logger = LoggerFactory.getLogger(TutorialTests.class);

    @Test
    public void tutorialTest() throws IOException, InterruptedException {
        ItemDto itemDto = pageFactory.getTutorial().createItemsViaTutorial();

        //Download file
        File file = new File("test.jar");
        if (file.exists())
            file.delete();
        assertFalse(file.exists());

        //TODO Gateway download test should be an isolated test and therefore separated from tutorial test.
        // I want to test the download feature and only if this works, then continue to test jar execution features using other tests?
        //TODO Gateway download should be hidden behind the API.
        downloadGateway(itemDto);

        assertTrue(file.exists());
        assertTrue(file.length() > 1000);

        Thread jarThread = executeJar();
        Thread.sleep(30000);
        jarThread.interrupt();

        //TODO Extend the API to make calls by

        cleanup(itemDto, file);
    }

    private void downloadGateway(ItemDto itemDto) throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();
        String firstPartOfCommand = "cmd /c curl -v ";
        if (host.equals("localhost"))
            firstPartOfCommand += "-k ";
        String command = firstPartOfCommand + "--cookie \"SESSION=" + sessionCookie.getValue() + "\" --output ./test.jar " + itemDto.gatewayDownloadLink;
        Process pr = rt.exec(command);
        pr.waitFor();
    }

    private void cleanup(ItemDto itemDto, File file) {
        AdminServiceGateways adminServiceGateways = pageFactory.getServiceGateways();
        String gatewayName = adminServiceGateways.getGatewayNameByIdentifier(itemDto.gatewayIdentifier);
        adminServiceGateways.deleteGateway(gatewayName);

        pageFactory.getApiKeys().deleteApiKey(itemDto.apiKeyValue);
        pageFactory.getServiceOfferings().deleteService(itemDto.serviceName);

        file.delete();
        assertFalse(file.exists());
    }

    private Thread executeJar() {
        Thread jarExecutionThread = new Thread(() -> {
            String line = "java -jar ./test.jar";
            CommandLine cmdLine = CommandLine.parse(line);
            DefaultExecutor executor = new DefaultExecutor();
            ExecuteWatchdog watchdog = new ExecuteWatchdog(120000);
            executor.setWatchdog(watchdog);
            try {
                executor.execute(cmdLine);
            } catch (IOException e) {
                logger.info("The execution of the jar file was interrupted.");
            }
        });
        jarExecutionThread.start();
        return jarExecutionThread;
    }

}
