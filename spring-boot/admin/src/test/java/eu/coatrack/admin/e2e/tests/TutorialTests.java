package eu.coatrack.admin.e2e.tests;

/*-
 * #%L
 * coatrack-admin
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

        //TODO Gateway download test should be an isolated test and therefore separated from tutorial test.
        // I want to test the download feature and only if this works, then continue to test jar execution features using other tests?
        //TODO Gateway download should be hidden behind the API.
        File file = downloadGateway(itemDto.gatewayDownloadLink);

        Thread jarThread = executeJar(file);

        try {
            boolean result = pageFactory.getApiKeyTester().isServiceAccessibleWithApiKey(itemDto.serviceId, itemDto.apiKeyValue);
            assertTrue(result);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            jarThread.interrupt();
            cleanup(itemDto, file);
        }
        //TODO Implement service call testing logic. Wrap this in a try-catch block and execute 'interrupt()' and 'cleanup()' in a finally block.
        //TODO Add exception logic. E.g. when a download fails, then a 'GatewayDownloadFailedException' should be thrown immediately.
        //TODO Extend the API to make with a function that call a service using an API key.
    }

    private File downloadGateway(String gatewayDownloadLink) throws IOException, InterruptedException {
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

    private void cleanup(ItemDto itemDto, File file) {
        AdminServiceGateways adminServiceGateways = pageFactory.getServiceGateways();
        String gatewayName = adminServiceGateways.getGatewayNameByIdentifier(itemDto.gatewayIdentifier);
        adminServiceGateways.deleteGateway(gatewayName);

        pageFactory.getApiKeys().deleteApiKey(itemDto.apiKeyValue);
        pageFactory.getServiceOfferings().deleteService(itemDto.serviceName);

        file.delete();
        assertFalse(file.exists());
    }

    private Thread executeJar(File file) throws InterruptedException {
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

}
