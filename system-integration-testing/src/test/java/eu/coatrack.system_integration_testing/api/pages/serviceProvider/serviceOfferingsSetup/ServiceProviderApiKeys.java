package eu.coatrack.system_integration_testing.api.pages.serviceProvider.serviceOfferingsSetup;

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
import org.openqa.selenium.WebElement;

import java.util.List;

import static eu.coatrack.system_integration_testing.api.UtilFactory.driver;
import static eu.coatrack.system_integration_testing.api.UtilFactory.serviceProviderApiKeyTableUtils;
import static eu.coatrack.system_integration_testing.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.system_integration_testing.configuration.PageConfiguration.serviceProviderApiKeysUrl;
import static eu.coatrack.system_integration_testing.configuration.PageConfiguration.username;
import static eu.coatrack.system_integration_testing.configuration.TableConfiguration.*;

public class ServiceProviderApiKeys {

    public void deleteAllApiKeys() {
        serviceProviderApiKeyTableUtils.deleteAllItem();
    }

    public String createApiKey(String serviceName) {
        driver.get(serviceProviderApiKeysUrl);

        List<String> listOfApiKeyValuesBeforeCreation = serviceProviderApiKeyTableUtils.getListOfColumnValues(2);
        workThroughApiKeyCreationMenuForService(serviceName);
        List<String> listOfApiKeyValuesAfterCreation = serviceProviderApiKeyTableUtils.getListOfColumnValues(2);;

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
        return serviceProviderApiKeyTableUtils.isItemWithinList(apiKeyValue);
    }

    public void deleteApiKey(String apiKeyValue) {
        serviceProviderApiKeyTableUtils.deleteItem(apiKeyValue);
    }

    public void clickOnCalenderButtonOfApiKey(String apiKeyValue) {
        serviceProviderApiKeyTableUtils.clickOnButton(apiKeyValue, serviceProviderApiKeysCalenderButtonColumn, serviceProviderApiKeysCalenderButtonClassName);
        sleepMillis(2000);
        driver.get(serviceProviderApiKeysUrl);
    }

    public void clickOnDetailsButtonOfApiKey(String apiKeyValue) {
        serviceProviderApiKeyTableUtils.clickOnButton(apiKeyValue, serviceProviderApiKeysDetailsButtonColumn, serviceProviderApiKeysDetailsButtonClassName);
    }

    public void clickOnEditButtonOfApiKey(String apiKeyValue) {
        serviceProviderApiKeyTableUtils.clickOnButton(apiKeyValue, serviceProviderApiKeysEditButtonColumn, serviceProviderApiKeysEditButtonClassName);
    }
}
