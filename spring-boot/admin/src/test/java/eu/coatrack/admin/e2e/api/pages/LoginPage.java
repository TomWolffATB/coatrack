package eu.coatrack.admin.e2e.api.pages;

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
import org.openqa.selenium.WebDriver;

import static eu.coatrack.admin.e2e.configuration.PageConfiguration.adminDashboardUrl;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.startpageUrl;

//TODO The tests should be enabled to login to dev.coatrack.eu which includes the account creation.
public class LoginPage {

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void loginToGithub(String username, String password){
        driver.get(startpageUrl);
        driver.findElement(By.cssSelector("ul:nth-child(1) > li:nth-child(4) > a")).click();
        driver.findElement(By.id("login_field")).click();
        driver.findElement(By.id("login_field")).sendKeys(username);
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.name("commit")).click();
        driver.get(adminDashboardUrl);
    }

}
