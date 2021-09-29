package eu.coatrack.admin.e2e.api.tools;

import eu.coatrack.admin.e2e.api.serviceProvider.AdminTutorial;
import eu.coatrack.admin.e2e.api.serviceProvider.ItemDto;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminApiKeys;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceGateways;
import eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup.AdminServiceOfferings;
import eu.coatrack.admin.e2e.exceptions.GatewayRunnerInitializationException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static eu.coatrack.admin.e2e.configuration.CookieInjector.sessionCookie;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.gatewayAccessUrl;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.host;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GatewayRunner {

    private static final Logger logger = LoggerFactory.getLogger(GatewayRunner.class);

    private static WebDriver driver;
    private static GatewayRunner gatewayRunner = null;

    private final AdminTutorial adminTutorial;
    private final AdminApiKeys adminApiKeys;
    private final AdminServiceGateways adminServiceGateways;
    private final AdminServiceOfferings adminServiceOfferings;

    private Thread jarThread;
    private ItemDto itemDto;
    private File file;

    private GatewayRunner() {
        adminTutorial = new AdminTutorial(driver);
        adminApiKeys = new AdminApiKeys(driver);
        adminServiceGateways = new AdminServiceGateways(driver);
        adminServiceOfferings = new AdminServiceOfferings(driver);
    }

    public static GatewayRunner createAndRunGateway(WebDriver driver) {
        GatewayRunner.driver = driver;
        if (gatewayRunner != null)
            gatewayRunner.stopGatewayAndCleanup();

        gatewayRunner = new GatewayRunner();
        try {
            gatewayRunner.itemDto = gatewayRunner.adminTutorial.createItemsViaTutorial();
            gatewayRunner.file = downloadGateway(gatewayRunner.itemDto.gatewayDownloadLink);
            gatewayRunner.jarThread = executeGatewayJar(gatewayRunner.file);
        } catch (Exception e) {
            if (gatewayRunner.jarThread != null)
                gatewayRunner.stopGatewayAndCleanup();
            throw new GatewayRunnerInitializationException("Something went wrong during the initialization process.", e);
        }
        return gatewayRunner;
    }

    private static File downloadGateway(String gatewayDownloadLink) throws IOException, InterruptedException {
        File file = new File("test.jar");
        if (file.exists())
            file.delete();
        assertFalse(file.exists());

        Runtime rt = Runtime.getRuntime();
        String firstPartOfCommand = "cmd /c curl -v ";
        if (host.equals("localhost"))
            firstPartOfCommand += "-k ";
        String command = firstPartOfCommand + "--cookie \"SESSION=" + sessionCookie.getValue() + "\" --output ./test.jar " + gatewayDownloadLink;
        Process pr = rt.exec(command);
        pr.waitFor();

        assertTrue(file.exists());
        assertTrue(file.length() > 1000);

        return file;
    }

    private static Thread executeGatewayJar(File file) throws InterruptedException {
        Thread jarExecutionThread = new Thread(() -> {
            String line = "java -jar " + file.getPath();
            CommandLine cmdLine = CommandLine.parse(line);
            DefaultExecutor executor = new DefaultExecutor();
            ExecuteWatchdog watchdog = new ExecuteWatchdog(300000);
            executor.setWatchdog(watchdog);
            try {
                executor.execute(cmdLine);
            } catch (IOException e) {
                logger.info("The execution of the jar file was interrupted.");
            }
        });
        jarExecutionThread.setDaemon(true);
        jarExecutionThread.start();

        //TODO Wait until the gateway is setup and listens on port 8088. Maybe by fetching the logs?
        Thread.sleep(30000);

        return jarExecutionThread;
    }

    public void stopGatewayAndCleanup() {
        jarThread.interrupt();

        String gatewayName = adminServiceGateways.getGatewayNameByIdentifier(itemDto.gatewayIdentifier);
        adminServiceGateways.deleteGateway(gatewayName);

        adminApiKeys.deleteApiKey(itemDto.apiKeyValue);
        adminServiceOfferings.deleteService(itemDto.serviceName);

        file.delete();
        assertFalse(file.exists());
        GatewayRunner.gatewayRunner = null;
    }

    public void makeValidServiceCall() {
        boolean result = accessServiceUsingApiKey(itemDto.serviceId, itemDto.apiKeyValue);
        assertTrue(result);
    }

    private boolean accessServiceUsingApiKey(String serviceId, String apiKeyValue) {
        String servicesAccessUrl = gatewayAccessUrl + "/" + serviceId + "?api-key=" + apiKeyValue;
        driver.get(servicesAccessUrl);
        return driver.findElement(By.cssSelector("h1")).getText().equals("Example Domain");
    }

    public void makeInvalidServiceCallUsingWrongServiceName() {
        String wrongServiceId = itemDto.serviceId + "x";
        boolean result = accessServiceUsingApiKey(wrongServiceId, itemDto.apiKeyValue);
        assertFalse(result);
    }

    public void makeInvalidServiceCallUsingWrongApiKeyValue() {
        String wrongApiKeyValue = itemDto.apiKeyValue + "x";
        boolean result = accessServiceUsingApiKey(itemDto.serviceId, wrongApiKeyValue);
        assertFalse(result);
    }

    //TODO Remove assertions and creation Exception logic instead. Check other classes too.

}
