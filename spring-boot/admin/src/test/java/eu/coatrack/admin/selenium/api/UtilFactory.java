package eu.coatrack.admin.selenium.api;

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

import eu.coatrack.admin.selenium.api.tools.WaiterUtils;
import eu.coatrack.admin.selenium.api.tools.table.TableUtils;
import org.openqa.selenium.WebDriver;

import static eu.coatrack.admin.selenium.api.tools.table.TableType.*;

public class UtilFactory {

    public static WebDriver driver = PageFactory.driver;

    public static WaiterUtils waiterUtils = new WaiterUtils();

    public static TableUtils serviceProviderApiKeyTableUtils = new TableUtils(PROVIDER_APIKEY_TABLE);
    public static TableUtils serviceProviderGatewayTableUtils = new TableUtils(PROVIDER_GATEWAY_TABLE);
    public static TableUtils serviceProviderServiceTableUtils = new TableUtils(PROVIDER_SERVICE_TABLE);

    public static TableUtils serviceConsumerApiKeyTableUtils = new TableUtils(CONSUMER_APIKEY_TABLE);
    public static TableUtils serviceConsumerServiceTableUtils = new TableUtils(CONSUMER_SERVICE_TABLE);

}
