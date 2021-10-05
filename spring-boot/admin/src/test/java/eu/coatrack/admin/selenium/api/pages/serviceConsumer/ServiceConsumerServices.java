package eu.coatrack.admin.selenium.api.pages.serviceConsumer;

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

import eu.coatrack.admin.selenium.api.tools.table.TableUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static eu.coatrack.admin.selenium.api.tools.table.TableType.*;
import static eu.coatrack.admin.selenium.configuration.TableConfiguration.*;

public class ServiceConsumerServices {

    private final TableUtils consumerServiceTableUtils;
    private final TableUtils consumerApiKeyTableUtils;

    public ServiceConsumerServices(WebDriver driver) {
        consumerServiceTableUtils = new TableUtils(driver, CONSUMER_SERVICE_TABLE);
        consumerApiKeyTableUtils = new TableUtils(driver, CONSUMER_APIKEY_TABLE);
    }

    public String createApiKeyFromPublicService(String serviceName) {
        List<String> apiKeyListBeforeApiKeyCreation = consumerApiKeyTableUtils.getListOfColumnValues(serviceConsumerApiKeysDefaultNameColumn);
        clickOnApiKeyGenerationButtonInServiceRow(serviceName);
        List<String> apiKeyListAfterApiKeyCreation = consumerApiKeyTableUtils.getListOfColumnValues(serviceConsumerApiKeysDefaultNameColumn);

        apiKeyListAfterApiKeyCreation.removeAll(apiKeyListBeforeApiKeyCreation);
        String apiKeyValue = apiKeyListAfterApiKeyCreation.get(0);
        return apiKeyValue;
    }

    private void clickOnApiKeyGenerationButtonInServiceRow(String serviceName) {
        WebElement rowOfService = consumerServiceTableUtils.getItemRows().stream()
                .filter(row -> consumerServiceTableUtils.getCellInColumn(row, serviceConsumerServicesDefaultNameColumn).getText().contains(serviceName))
                .findFirst().get();
        consumerApiKeyTableUtils.getCellInColumn(rowOfService, serviceConsumerServicesApiKeyGenerationColumn)
                .findElement(By.cssSelector("button")).click();
    }
}
