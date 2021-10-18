package eu.coatrack.system_integration_testing.api.pages.serviceProvider;

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

import eu.coatrack.system_integration_testing.api.pages.AbstractDashboardTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static eu.coatrack.system_integration_testing.api.PageFactory.urlReachabilityTools;
import static eu.coatrack.system_integration_testing.api.UtilFactory.driver;
import static eu.coatrack.system_integration_testing.configuration.PageConfiguration.serviceProviderDashboardUrl;
import static eu.coatrack.system_integration_testing.configuration.PageConfiguration.username;

public class ServiceProviderDashboard extends AbstractDashboardTemplate {

    public ServiceProviderDashboard() {
        super(serviceProviderDashboardUrl);
    }

    public int getApiUsageTrend() {
        return getIntegerValueOfElementWithId("callsDiff");
    }

    public int getNumberOfUsers() {
        return getIntegerValueOfElementWithId("users");
    }

    public int getCallsOfLoggedInUser() {
        urlReachabilityTools.fastVisit(serviceProviderDashboardUrl);
        List<String> list = driver.findElement(By.id("userStatisticsTable")).findElements(By.cssSelector("span"))
                .stream().map(WebElement::getText).collect(Collectors.toList());

        for (int i = 0; i < list.size(); i += 2){
            if (list.get(i).equals(username))
                return Integer.parseInt(list.get(i+1));
        }
        return 0;
    }

}
