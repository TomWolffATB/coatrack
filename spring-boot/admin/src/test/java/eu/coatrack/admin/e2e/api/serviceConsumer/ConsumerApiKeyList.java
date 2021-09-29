package eu.coatrack.admin.e2e.api.serviceConsumer;

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

import eu.coatrack.admin.e2e.api.tools.TableType;
import eu.coatrack.admin.e2e.api.tools.TableUtils;
import org.openqa.selenium.WebDriver;

public class ConsumerApiKeyList {

    private final TableUtils consumerApiKeyTableUtils;

    public ConsumerApiKeyList(WebDriver driver) {
        consumerApiKeyTableUtils = new TableUtils(driver, TableType.CONSUMER_APIKEY_TABLE);
    }

    public boolean isApiKeyWithinList(String apiKeyValue){
        return consumerApiKeyTableUtils.isItemWithinList(apiKeyValue);
    }

    public void deletePublicApiKey(String apiKeyValue) {
        consumerApiKeyTableUtils.deleteItem(apiKeyValue);
    }
}
