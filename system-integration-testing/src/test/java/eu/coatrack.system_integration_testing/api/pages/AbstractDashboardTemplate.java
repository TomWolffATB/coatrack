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

import static eu.coatrack.system_integration_testing.api.PageFactory.urlReachabilityTools;
import static eu.coatrack.system_integration_testing.api.UtilFactory.driver;

public abstract class AbstractDashboardTemplate {

    protected final String targetUrl;

    public AbstractDashboardTemplate(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public int getTotalApiCalls() {
        return getIntegerValueOfElementWithId("callsThisPeriod");
    }

    public int getErrorCount() {
        return getIntegerValueOfElementWithId("errorsThisPeriod");
    }

    protected int getIntegerValueOfElementWithId(String elementId){
        urlReachabilityTools.fastVisit(targetUrl);
        WebElement callsThisPeriod = driver.findElement(By.id(elementId));
        return Integer.parseInt(callsThisPeriod.getText());
    }
}
