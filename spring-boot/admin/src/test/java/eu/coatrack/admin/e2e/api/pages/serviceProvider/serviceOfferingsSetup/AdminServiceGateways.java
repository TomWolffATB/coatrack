package eu.coatrack.admin.e2e.api.pages.serviceProvider.serviceOfferingsSetup;

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

import eu.coatrack.admin.e2e.api.tools.table.TableType;
import eu.coatrack.admin.e2e.api.tools.table.TableUtils;
import eu.coatrack.admin.e2e.api.tools.WaiterUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Random;

import static eu.coatrack.admin.e2e.configuration.PageConfiguration.adminGatewayListUrl;
import static eu.coatrack.admin.e2e.configuration.TableConfiguration.*;

public class AdminServiceGateways {

    private final WebDriver driver;
    private final WaiterUtils waiterUtils;
    private final TableUtils gatewayTableUtils;

    public AdminServiceGateways(WebDriver driver) {
        this.driver = driver;
        waiterUtils = new WaiterUtils(driver);
        gatewayTableUtils = new TableUtils(driver, TableType.GATEWAY_TABLE);
    }

    public String createGateway() {
        driver.get(adminGatewayListUrl);
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
        return gatewayTableUtils.isItemWithinList(gatewayName);
    }

    public void deleteGateway(String gatewayName) {
        gatewayTableUtils.deleteItem(gatewayName);
    }

    public void deleteAllGateways() {
        gatewayTableUtils.deleteAllItem();
    }

    public void clickOnDetailsButtonOfGateway(String gatewayName) {
        gatewayTableUtils.clickOnButton(gatewayName, adminGatewaysDetailsButtonColumn, adminGatewaysDetailsButtonClassName);
    }

    public void clickOnEditButtonOfGateway(String gatewayName) {
        gatewayTableUtils.clickOnButton(gatewayName, adminGatewaysEditButtonColumn, adminGatewaysEditButtonClassName);
    }

    public String getGatewayNameByIdentifier(String identifier){
        return gatewayTableUtils.getColumnTextFromItemRow(identifier, adminGatewaysIdColumn, adminGatewaysNameColumn);
    }
}
