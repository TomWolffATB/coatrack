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
import org.openqa.selenium.WebElement;

import java.util.List;

import static eu.coatrack.admin.e2e.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.adminApiKeyListUrl;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.username;
import static eu.coatrack.admin.e2e.configuration.TableConfiguration.*;

public class AdminApiKeys {

    private final WebDriver driver;
    private final TableUtils apiKeyTableUtils;
    private final WaiterUtils waiterUtils;

    public AdminApiKeys(WebDriver driver) {
        this.driver = driver;
        apiKeyTableUtils = new TableUtils(driver, TableType.APIKEY_TABLE);
        waiterUtils = new WaiterUtils(driver);
    }

    public void deleteAllApiKeys() {
        apiKeyTableUtils.deleteAllItem();
    }

    public String createApiKey(String serviceName) {
        driver.get(adminApiKeyListUrl);

        List<String> listOfApiKeyValuesBeforeCreation = apiKeyTableUtils.getListOfColumnValues(2);
        workThroughApiKeyCreationMenuForService(serviceName);
        List<String> listOfApiKeyValuesAfterCreation = apiKeyTableUtils.getListOfColumnValues(2);;

        listOfApiKeyValuesAfterCreation.removeAll(listOfApiKeyValuesBeforeCreation);
        String valueOfNewApiKey = listOfApiKeyValuesAfterCreation.get(0);
        return valueOfNewApiKey;
    }

    private void workThroughApiKeyCreationMenuForService(String serviceName) {
        driver.findElement(By.linkText("Create API Key")).click();

        WebElement dropdown = driver.findElement(By.id("selectedServiceId"));
        dropdown.findElements(By.cssSelector("option")).stream().filter(option -> option.getText().contains(serviceName)).findFirst().get().click();

        driver.findElement(By.id("githubUserSearchCriteria")).click();
        driver.findElement(By.id("githubUserSearchCriteria")).sendKeys(username);
        driver.findElement(By.id("githubUserSearchButton")).click();

        sleepMillis(1000);
        driver.findElement(By.cssSelector("td:nth-child(2)")).click();
        driver.findElement(By.id("saveApiKeyButton")).click();
    }

    public boolean isApiKeyWithinList(String apiKeyValue) {
        return apiKeyTableUtils.isItemWithinList(apiKeyValue);
    }

    public void deleteApiKey(String apiKeyValue) {
        apiKeyTableUtils.deleteItem(apiKeyValue);
    }

    public void clickOnCalenderButtonOfApiKey(String apiKeyValue) {
        apiKeyTableUtils.clickOnButton(apiKeyValue, adminApiKeysCalenderButtonColumn, adminApiKeysCalenderButtonClassName);
        sleepMillis(2000);
        driver.get(adminApiKeyListUrl);
    }

    public void clickOnDetailsButtonOfApiKey(String apiKeyValue) {
        apiKeyTableUtils.clickOnButton(apiKeyValue, adminApiKeysDetailsButtonColumn, adminApiKeysDetailsButtonClassName);
    }

    public void clickOnEditButtonOfApiKey(String apiKeyValue) {
        apiKeyTableUtils.clickOnButton(apiKeyValue, adminApiKeysEditButtonColumn, adminApiKeysEditButtonClassName);
    }
}
