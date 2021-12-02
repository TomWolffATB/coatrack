package eu.coatrack.system_integration_testing.api.tools;

/*-
 * #%L
 * system-integration-testing
 * %%
 * Copyright (C) 2013 - 2021 Corizon | Institut für angewandte Systemtechnik Bremen GmbH (ATB)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import eu.coatrack.system_integration_testing.api.pages.serviceProvider.ItemDetails;
import eu.coatrack.system_integration_testing.exceptions.FileCouldNotBeDeletedException;
import eu.coatrack.system_integration_testing.exceptions.GatewayDownloadFailedException;
import eu.coatrack.system_integration_testing.exceptions.GatewayRunnerInitializationException;
import eu.coatrack.system_integration_testing.exceptions.ServiceCouldNotBeAccessedUsingApiKeyException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

import static eu.coatrack.system_integration_testing.api.PageFactory.*;
import static eu.coatrack.system_integration_testing.api.UtilFactory.driver;
import static eu.coatrack.system_integration_testing.api.tools.WaiterUtils.waitUpToTwoMinutesUntilHostListensOnPort;
import static eu.coatrack.system_integration_testing.configuration.CookieInjector.sessionCookie;
import static eu.coatrack.system_integration_testing.configuration.PageConfiguration.host;
import static eu.coatrack.system_integration_testing.configuration.PageConfiguration.localGatewayAccessUrl;

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

    private void executeGatewayDownload(String gatewayDownloadLink, String gatewayJarName) throws IOException {
        String gatewayDownloadCommand = createGatewayDownloadCommand(gatewayDownloadLink, gatewayJarName);
        CommandLine cmdLine = CommandLine.parse(gatewayDownloadCommand);
        DefaultExecutor executor = new DefaultExecutor();
        executor.execute(cmdLine);
    }

    private String createGatewayDownloadCommand(String gatewayDownloadLink, String gatewayJarFileName) {
        String firstPartOfCommand = "curl -v ";
        if (host.equals("localhost"))
            firstPartOfCommand += "-k ";
        return firstPartOfCommand + "--cookie \"SESSION=" + sessionCookie.getValue() + "\" --output ./" + gatewayJarFileName + " " + gatewayDownloadLink;
    }

    private static Thread executeGatewayJar(File file) {
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
        waitUpToTwoMinutesUntilHostListensOnPort("localhost", 8088);

        return jarExecutionThread;
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
        boolean wasServiceCallSuccessful = tryToAccessServiceUsingApiKey(itemDetails.serviceId, itemDetails.apiKeyValue);
        if (!wasServiceCallSuccessful)
            throw new ServiceCouldNotBeAccessedUsingApiKeyException("Api key " + itemDetails.apiKeyValue +
                    " could not access the service with the ID " + itemDetails.serviceId + ".");
    }

    private boolean tryToAccessServiceUsingApiKey(String serviceId, String apiKeyValue) {
        String servicesAccessUrl = localGatewayAccessUrl + "/" + serviceId + "?api-key=" + apiKeyValue;
        driver.get(servicesAccessUrl);
        return driver.findElement(By.cssSelector("h1")).getText().equals("Example Domain");
    }

    public ItemDetails getItemDetails() {
        return itemDetails;
    }

}
