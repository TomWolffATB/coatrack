package eu.coatrack.admin.selenium.api.tools;

import eu.coatrack.admin.selenium.api.pages.serviceProvider.ItemDetails;
import eu.coatrack.admin.selenium.exceptions.FileCouldNotBeDeletedException;
import eu.coatrack.admin.selenium.exceptions.GatewayDownloadFailedException;
import eu.coatrack.admin.selenium.exceptions.GatewayRunnerInitializationException;
import eu.coatrack.admin.selenium.exceptions.ServiceCouldNotBeAccessedUsingApiKeyException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import static eu.coatrack.admin.selenium.api.PageFactory.*;
import static eu.coatrack.admin.selenium.api.UtilFactory.driver;
import static eu.coatrack.admin.selenium.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.selenium.configuration.CookieInjector.sessionCookie;
import static eu.coatrack.admin.selenium.configuration.PageConfiguration.localGatewayAccessUrl;
import static eu.coatrack.admin.selenium.configuration.PageConfiguration.host;

public class GatewayRunner {

    private static final Logger logger = LoggerFactory.getLogger(GatewayRunner.class);

    private Thread jarThread;
    private ItemDetails itemDetails;
    private File gatewayJar;

    public void executeRunner(){
        if (jarThread != null)
            stopGatewayAndCleanup();

        try {
            itemDetails = serviceProviderTutorial.createItemsViaTutorial();
            gatewayJar = downloadGateway(itemDetails.gatewayDownloadLink);
            jarThread = executeGatewayJar(gatewayJar);
        } catch (Exception e) {
            if (jarThread != null)
                stopGatewayAndCleanup();
            throw new GatewayRunnerInitializationException("Something went wrong during the initialization process.", e);
        }
    }

    private File downloadGateway(String gatewayDownloadLink) throws IOException, InterruptedException {
        File gatewayJar = getGatewayJarFile();
        executeGatewayDownload(gatewayDownloadLink, gatewayJar.getName());
        if (!gatewayJar.exists() || gatewayJar.length() < 1000)
            throw new GatewayDownloadFailedException("Trying to download the Gateway '" + itemDetails.gatewayName + "' an error occurred.");
        return gatewayJar;
    }

    private File getGatewayJarFile() {
        File gatewayJarFile = new File("gateway.jar");
        if (gatewayJarFile.exists())
            gatewayJarFile.delete();
        if (gatewayJarFile.exists())
            throw new FileCouldNotBeDeletedException("The file " + gatewayJarFile.getName() + "could not be deleted.");
        return gatewayJarFile;
    }

    private void executeGatewayDownload(String gatewayDownloadLink, String gatewayJarName) throws IOException, InterruptedException {
        Runtime rt = Runtime.getRuntime();
        String gatewayDownloadCommand = createGatewayDownloadCommand(gatewayDownloadLink, gatewayJarName);
        Process pr = rt.exec(gatewayDownloadCommand);
        pr.waitFor();
    }

    private String createGatewayDownloadCommand(String gatewayDownloadLink, String gatewayJarFileName) {
        String firstPartOfCommand;
        if (host.equals("localhost"))
            firstPartOfCommand = "cmd /c curl -v -k ";
        else
            firstPartOfCommand = "cmd /c wsl curl -v ";
        return firstPartOfCommand + "--cookie \"SESSION=" + sessionCookie.getValue() + "\" --output ./" + gatewayJarFileName + " " + gatewayDownloadLink;
    }

    private static Thread executeGatewayJar(File file) throws InterruptedException {
        Thread jarExecutionThread = new Thread(() -> {
            String line = "java -jar " + file.getPath();
            CommandLine cmdLine = CommandLine.parse(line);
            DefaultExecutor executor = new DefaultExecutor();
            try {
                executor.execute(cmdLine);
            } catch (IOException e) {
                logger.info("The execution of the jar file was interrupted.");
            }
        });
        jarExecutionThread.setDaemon(true);
        jarExecutionThread.start();
        waitUntilGatewayIsInitialized();

        return jarExecutionThread;
    }

    private static void waitUntilGatewayIsInitialized() {
        boolean isConnectionEstablished = false;
        while (!isConnectionEstablished){
            try {
                Socket socket = new Socket("localhost", 8088);
                isConnectionEstablished = true;
                socket.close();
            } catch (Exception e){
                logger.debug("Connection to Gateway could not yet be established.");
                sleepMillis(1000);
            }
        }
    }

    public void stopGatewayAndCleanup() {
        jarThread.interrupt();
        jarThread = null;

        String gatewayName = serviceProviderGateways.getGatewayNameByIdentifier(itemDetails.gatewayIdentifier);
        serviceProviderGateways.deleteGateway(gatewayName);

        serviceProviderApiKeys.deleteApiKey(itemDetails.apiKeyValue);
        serviceProviderServices.deleteService(itemDetails.serviceName);

        gatewayJar.delete();
        if (gatewayJar.exists())
            throw new FileCouldNotBeDeletedException("The file " + gatewayJar.getName() + "could not be deleted.");
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
