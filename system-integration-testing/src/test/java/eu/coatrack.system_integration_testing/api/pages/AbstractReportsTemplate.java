package eu.coatrack.system_integration_testing.api.pages;

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
import static eu.coatrack.system_integration_testing.api.UtilFactory.waiterUtils;
import static eu.coatrack.system_integration_testing.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.system_integration_testing.configuration.PageConfiguration.serviceProviderReportsUrl;
import static eu.coatrack.system_integration_testing.configuration.PageConfiguration.username;

public abstract class AbstractReportsTemplate {

    private final String reportsPageUrl;

    public AbstractReportsTemplate(String reportsPageUrl) {
        this.reportsPageUrl = reportsPageUrl;
    }

    public int getNumberOfServiceCalls(String serviceName){
        driver.get(reportsPageUrl);
        driver.findElement(By.id("selectedServiceId")).findElements(By.cssSelector("option")).stream()
                .filter(e -> e.getText().contains(serviceName)).findFirst().get().click();

        if (reportsPageUrl.equals(serviceProviderReportsUrl)) {
            driver.findElement(By.id("selectedApiConsumerUserId")).findElements(By.cssSelector("option")).stream()
                    .filter(e -> e.getText().contains(username)).findFirst().get().click();
        }

        driver.findElement(By.id("searchBtn")).click();
        waiterUtils.waitForElementWithId("reportTable");
        List<WebElement> resultRowCells = driver.findElement(By.id("reportTable")).findElement(By.cssSelector("tbody")).findElements(By.cssSelector("td"));
        if (resultRowCells.size() == 0 || resultRowCells.get(0).getText().contains("No data available in table"))
            return 0;
        else
            return Integer.parseInt(resultRowCells.get(1).getText());
    }
}
