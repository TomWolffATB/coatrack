package eu.coatrack.system_integration_testing.api.tools;

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

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

import static eu.coatrack.system_integration_testing.api.UtilFactory.driver;

public class WaiterUtils {

    private static final Logger logger = LoggerFactory.getLogger(WaiterUtils.class);

    public static void waitUntilHostListensOnPort(String host, int port) {
        boolean isConnectionEstablished = false;
        while (!isConnectionEstablished){
            try {
                Socket socket = new Socket(host, port);
                isConnectionEstablished = true;
                socket.close();
            } catch (Exception e){
                logger.debug("Connection to host {}:{} could not yet be established.", host, port);
                sleepMillis(1000);
            }
        }
    }

    public void waitForElementWithId(String id) {
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.id(id)));
    }

    public void waitForElementWithCssSelector(String cssSelector) {
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.id(cssSelector)));
    }

    public static void sleepMillis(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e){
            logger.error("The sleep process failed.", e);
        }
    }

    public void waitUpToAMinuteForElementWithId(String id) {
        new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(By.id(id)));
    }
}
