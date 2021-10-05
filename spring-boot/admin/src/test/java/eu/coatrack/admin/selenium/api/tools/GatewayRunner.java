package eu.coatrack.admin.selenium.api.tools;

import eu.coatrack.admin.selenium.api.pages.serviceProvider.ServiceProviderTutorial;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.ItemDetails;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.serviceOfferingsSetup.ServiceProviderApiKeys;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.serviceOfferingsSetup.ServiceProviderGateways;
import eu.coatrack.admin.selenium.api.pages.serviceProvider.serviceOfferingsSetup.ServiceProviderServices;
import eu.coatrack.admin.selenium.exceptions.FileCouldNotBeDeletedException;
import eu.coatrack.admin.selenium.exceptions.GatewayDownloadFailedException;
import eu.coatrack.admin.selenium.exceptions.GatewayRunnerInitializationException;
import eu.coatrack.admin.selenium.exceptions.ServiceCouldNotBeAccessedUsingApiKeyException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;

import static eu.coatrack.admin.selenium.configuration.CookieInjector.sessionCookie;
import static eu.coatrack.admin.selenium.configuration.PageConfiguration.localGatewayAccessUrl;
import static eu.coatrack.admin.selenium.configuration.PageConfiguration.host;

public class GatewayRunner {

    private static final Logger logger = LoggerFactory.getLogger(GatewayRunner.class);
    private final String gatewayJarFileName = "gateway.jar";

    private final WebDriver driver;

    private final ServiceProviderTutorial serviceProviderTutorial;
    private final ServiceProviderApiKeys serviceProviderApiKeys;
    private final ServiceProviderGateways serviceProviderGateways;
    private final ServiceProviderServices serviceProviderServices;

    private Thread jarThread;
    private ItemDetails itemDetails;
    private File file;

    public GatewayRunner (WebDriver driver) {
        this.driver = driver;
        serviceProviderTutorial = new ServiceProviderTutorial(driver);
        serviceProviderApiKeys = new ServiceProviderApiKeys(driver);
        serviceProviderGateways = new ServiceProviderGateways(driver);
        serviceProviderServices = new ServiceProviderServices(driver);
    }

    public GatewayRunner executeRunner(){
        try {
            itemDetails = serviceProviderTutorial.createItemsViaTutorial();
            file = downloadGateway(itemDetails.gatewayDownloadLink);
            jarThread = executeGatewayJar(file);
        } catch (Exception e) {
            if (jarThread != null)
                stopGatewayAndCleanup();
            throw new GatewayRunnerInitializationException("Something went wrong during the initialization process.", e);
        }
        return this;
    }

    private void executeGatewayDownload(String gatewayDownloadLink) throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();
        //TODO This is very slow when used for coatrack.eu. Maybe 'wsl curl' would be faster.
        String firstPartOfCommand = "cmd /c curl -v ";
        if (host.equals("localhost"))
            firstPartOfCommand += "-k ";
        String command = firstPartOfCommand + "--cookie \"SESSION=" + sessionCookie.getValue() + "\" --output ./" + gatewayJarFileName + " " + gatewayDownloadLink;
        Process pr = rt.exec(command);
        pr.waitFor();
    }

    private File downloadGateway(String gatewayDownloadLink) throws IOException, InterruptedException {
        File file = getGatewayJarFile();
        executeGatewayDownload(gatewayDownloadLink);
        if (!file.exists() || file.length() < 1000)
            throw new GatewayDownloadFailedException("Trying to download the Gateway '" + itemDetails.gatewayName + "' an error occurred.");
        return file;
    }

    private File getGatewayJarFile() {
        File gatewayJarFile = new File(gatewayJarFileName);
        if (gatewayJarFile.exists())
            gatewayJarFile.delete();
        if (gatewayJarFile.exists())
            throw new FileCouldNotBeDeletedException("The file " + gatewayJarFile.getName() + "could not be deleted.");
        return gatewayJarFile;
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

        String gatewayName = serviceProviderGateways.getGatewayNameByIdentifier(itemDetails.gatewayIdentifier);
        serviceProviderGateways.deleteGateway(gatewayName);

        serviceProviderApiKeys.deleteApiKey(itemDetails.apiKeyValue);
        serviceProviderServices.deleteService(itemDetails.serviceName);

        file.delete();
        if (file.exists())
            throw new FileCouldNotBeDeletedException("The file " + file.getName() + "could not be deleted.");
    }

    public void makeValidServiceCall() {
        if (!isServiceAccessUsingApiKeySuccessful(itemDetails.serviceId, itemDetails.apiKeyValue))
            throw new ServiceCouldNotBeAccessedUsingApiKeyException("Api key " + itemDetails.apiKeyValue +
                    " could not access the service with the ID " + itemDetails.serviceId + ".");
    }

    private boolean isServiceAccessUsingApiKeySuccessful(String serviceId, String apiKeyValue) {
        String servicesAccessUrl = localGatewayAccessUrl + "/" + serviceId + "?api-key=" + apiKeyValue;
        driver.get(servicesAccessUrl);
        return driver.findElement(By.cssSelector("h1")).getText().equals("Example Domain");
    }

    public ItemDetails getItemDetails() {
        return itemDetails;
    }

}
