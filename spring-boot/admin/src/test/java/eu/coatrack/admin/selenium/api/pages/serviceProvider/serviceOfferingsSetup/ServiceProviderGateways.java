package eu.coatrack.admin.selenium.api.pages.serviceProvider.serviceOfferingsSetup;

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

import java.util.Random;

import static eu.coatrack.admin.selenium.api.UtilFactory.*;
import static eu.coatrack.admin.selenium.configuration.PageConfiguration.serviceProviderGatewaysUrl;
import static eu.coatrack.admin.selenium.configuration.TableConfiguration.*;

public class ServiceProviderGateways {

    public String createGateway() {
        driver.get(serviceProviderGatewaysUrl);
        String gatewayName = "my-gateway-" + (new Random().nextInt());
        workThroughGatewayCreationMenu(gatewayName);
        return gatewayName;
    }

    private void workThroughGatewayCreationMenu(String gatewayName) {
        driver.findElement(By.linkText("Create Service Gateway")).click();
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys(gatewayName);
        driver.findElement(By.id("proxyPublicUrlInputField")).click();
        driver.findElement(By.id("proxyPublicUrlInputField")).sendKeys("https://mysite.com:8080");
        driver.findElement(By.id("port")).click();
        driver.findElement(By.id("port")).sendKeys("8080");
        driver.findElement(By.id("description")).click();
        driver.findElement(By.id("description")).sendKeys("some Description");
        driver.findElement(By.id("save")).click();
        waiterUtils.waitUpToAMinuteForElementWithId("proxiesTable");
    }

    public boolean isGatewayWithinList(String gatewayName) {
        return serviceProviderGatewayTableUtils.isItemWithinList(gatewayName);
    }

    public void deleteGateway(String gatewayName) {
        serviceProviderGatewayTableUtils.deleteItem(gatewayName);
    }

    public void deleteAllGateways() {
        serviceProviderGatewayTableUtils.deleteAllItem();
    }

    public void clickOnDetailsButtonOfGateway(String gatewayName) {
        serviceProviderGatewayTableUtils.clickOnButton(gatewayName, serviceProviderGatewaysDetailsButtonColumn, serviceProviderGatewaysDetailsButtonClassName);
    }

    public void clickOnEditButtonOfGateway(String gatewayName) {
        serviceProviderGatewayTableUtils.clickOnButton(gatewayName, serviceProviderGatewaysEditButtonColumn, serviceProviderGatewaysEditButtonClassName);
    }

    public String getGatewayNameByIdentifier(String identifier){
        return serviceProviderGatewayTableUtils.getColumnTextFromItemRow(identifier, serviceProviderGatewaysIdColumn, serviceProviderGatewaysNameColumn);
    }
}
