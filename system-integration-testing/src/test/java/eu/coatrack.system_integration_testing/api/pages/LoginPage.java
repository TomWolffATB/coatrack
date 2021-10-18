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

import static eu.coatrack.system_integration_testing.api.UtilFactory.driver;
import static eu.coatrack.system_integration_testing.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.system_integration_testing.configuration.PageConfiguration.serviceProviderDashboardUrl;

public class LoginPage {

    public void loginToGithub(String username, String password){
        driver.get(serviceProviderDashboardUrl);

        driver.findElement(By.id("login_field")).click();
        driver.findElement(By.id("login_field")).sendKeys(username);
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.name("commit")).click();

        sleepMillis(2000);
        if (driver.getPageSource().contains("Register New"))
            createNewAccount();

        driver.get(serviceProviderDashboardUrl);
    }

    private void createNewAccount() {
        driver.findElement(By.id("firstname")).sendKeys("John");
        driver.findElement(By.name("lastname")).sendKeys("Connor");
        driver.findElement(By.name("company")).sendKeys("Skynet");
        driver.findElement(By.name("submit")).click();
    }

}
