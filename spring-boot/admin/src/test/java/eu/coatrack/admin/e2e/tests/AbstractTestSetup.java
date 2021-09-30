package eu.coatrack.admin.e2e.tests;

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

import eu.coatrack.admin.e2e.api.PageFactory;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.firefox.FirefoxDriver;

//TODO Make all tests works flawlessly for coatrack.eu and dev.coatrack.eu.

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTestSetup {

    protected static PageFactory pageFactory = new PageFactory(new FirefoxDriver());

    static {
        pageFactory.getServiceOfferings().deleteAllServices();
        pageFactory.getServiceGateways().deleteAllGateways();
        pageFactory.getApiKeys().deleteAllApiKeys();
    }

}
