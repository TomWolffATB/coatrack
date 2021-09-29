package eu.coatrack.admin.e2e.api.tools;

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

import org.openqa.selenium.WebDriver;

import static eu.coatrack.admin.e2e.configuration.PageConfiguration.providerServiceUrl;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.startpageUrl;

public class ApiKeyTester {

    private final WebDriver driver;

    public ApiKeyTester(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isServiceAccessible(String serviceId, String apiKeyValue) throws InterruptedException {
        String callUrl = "http://localhost:8088/" + serviceId + "?api-key=" + apiKeyValue;
        driver.get(callUrl);
        return driver.getPageSource().contains("Example Domain");
    }
}
