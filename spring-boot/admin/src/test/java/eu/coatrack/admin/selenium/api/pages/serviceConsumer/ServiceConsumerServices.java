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

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static eu.coatrack.admin.selenium.api.UtilFactory.serviceConsumerApiKeyTableUtils;
import static eu.coatrack.admin.selenium.api.UtilFactory.serviceConsumerServiceTableUtils;
import static eu.coatrack.admin.selenium.configuration.TableConfiguration.*;

public class ServiceConsumerServices {

    public String createApiKeyFromPublicService(String serviceName) {
        List<String> apiKeyListBeforeApiKeyCreation = serviceConsumerApiKeyTableUtils.getListOfColumnValues(serviceConsumerApiKeysDefaultNameColumn);
        clickOnApiKeyGenerationButtonInServiceRow(serviceName);
        List<String> apiKeyListAfterApiKeyCreation = serviceConsumerApiKeyTableUtils.getListOfColumnValues(serviceConsumerApiKeysDefaultNameColumn);

        apiKeyListAfterApiKeyCreation.removeAll(apiKeyListBeforeApiKeyCreation);
        String apiKeyValue = apiKeyListAfterApiKeyCreation.get(0);
        return apiKeyValue;
    }

    private void clickOnApiKeyGenerationButtonInServiceRow(String serviceName) {
        WebElement rowOfService = serviceConsumerServiceTableUtils.getItemRows().stream()
                .filter(row -> serviceConsumerServiceTableUtils.getCellInColumn(row, serviceConsumerServicesDefaultNameColumn).getText().contains(serviceName))
                .findFirst().get();
        serviceConsumerApiKeyTableUtils.getCellInColumn(rowOfService, serviceConsumerServicesApiKeyGenerationColumn)
                .findElement(By.cssSelector("button")).click();
    }
}
