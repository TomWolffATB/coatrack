package eu.coatrack.admin.selenium.api.pages.serviceProvider.serviceOfferingsSetup;

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

import eu.coatrack.admin.selenium.api.tools.table.TableType;
import eu.coatrack.admin.selenium.api.tools.table.TableUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import java.util.Random;

import static eu.coatrack.admin.selenium.api.UtilFactory.driver;
import static eu.coatrack.admin.selenium.api.UtilFactory.serviceProviderServiceTableUtils;
import static eu.coatrack.admin.selenium.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.selenium.configuration.PageConfiguration.serviceProviderServicesUrl;
import static eu.coatrack.admin.selenium.configuration.PageConfiguration.exampleServiceUrl;
import static eu.coatrack.admin.selenium.configuration.TableConfiguration.*;


public class ServiceProviderServices {

    public String createPublicService() {
        driver.get(serviceProviderServicesUrl);
        String serviceName = "my-service-" + (new Random().nextInt());
        String serviceId = serviceName + "-id";
        workThroughServiceCreationMenu(serviceName, serviceId);
        return serviceName;
    }

    private void workThroughServiceCreationMenu(String serviceName, String serviceId) {
        driver.findElement(By.linkText("Create Service Offering")).click();
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys(serviceName);

        driver.findElement(By.id("localUrl")).click();
        driver.findElement(By.id("localUrl")).sendKeys(exampleServiceUrl);
        driver.findElement(By.id("uriIdentifier")).click();
        driver.findElement(By.id("uriIdentifier")).sendKeys(serviceId);
        driver.findElement(By.id("description")).click();
        driver.findElement(By.id("description")).sendKeys("Some Description");

        driver.findElement(By.linkText("Next")).sendKeys(Keys.RETURN);
        driver.findElement(By.linkText("Next")).sendKeys(Keys.RETURN);
        driver.findElement(By.id("freeButton")).click();
        driver.findElement(By.linkText("Next")).sendKeys(Keys.RETURN);
        sleepMillis(1000);
        driver.findElement(By.linkText("Finish")).sendKeys(Keys.RETURN);
        sleepMillis(1000);
    }

    public boolean isServiceWithinList(String serviceName) {
        return serviceProviderServiceTableUtils.isItemWithinList(serviceName);
    }

    public void deleteService(String serviceName){
        serviceProviderServiceTableUtils.deleteItem(serviceName);
    }

    public void deleteAllServices() {
        serviceProviderServiceTableUtils.deleteAllItem();
    }

    public void clickOnFirstEditButtonOfService(String serviceName) {
        serviceProviderServiceTableUtils.clickOnButton(serviceName, serviceProviderServicesFirstEditButtonColumn, serviceProviderServicesFirstEditButtonClassName);
    }

    public void clickDetailsButtonOfService(String serviceName) {
        serviceProviderServiceTableUtils.clickOnButton(serviceName, serviceProviderServicesDetailsButtonColumn, serviceProviderServicesDetailsButtonClassName);
    }

    public void clickOnSecondEditButtonOfService(String serviceName) {
        serviceProviderServiceTableUtils.clickOnButton(serviceName, serviceProviderServicesSecondEditButtonColumn, serviceProviderServicesSecondEditButtonClassName);
    }
}
