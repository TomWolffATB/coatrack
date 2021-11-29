package eu.coatrack.system_integration_testing.configuration;

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

import static eu.coatrack.system_integration_testing.configuration.PageConfiguration.startpageUrl;

public class TableConfiguration {

    public static final String
            emptyTableText = "No items yet",
            trashButtonClassName = "fa-trash",
            uiDialogClassName = "ui-dialog";

    public static final String
            serviceProviderApiKeysTableId = "apiKeyTable",
            serviceProviderApiKeysCalenderButtonClassName = "glyphicon-calendar",
            serviceProviderApiKeysDetailsButtonClassName = "fa-search-plus",
            serviceProviderApiKeysEditButtonClassName = "fa-pencil-square-o";
    public static final int
            serviceProviderApiKeysTrashButtonColumn = 6,
            serviceProviderApiKeysDefaultNameColumn = 2,
            serviceProviderApiKeysCalenderButtonColumn = 5,
            serviceProviderApiKeysDetailsButtonColumn = 6,
            serviceProviderApiKeysEditButtonColumn = 6;

    public static final String
            serviceProviderGatewaysTableId = "proxiesTable",
            serviceProviderGatewaysDetailsButtonClassName = "fa-bar-chart",
            serviceProviderGatewaysEditButtonClassName = "fa-pencil-square-o";
    public static final int
            serviceProviderGatewaysTrashButtonColumn = 9,
            serviceProviderGatewaysNameColumn = 0,
            serviceProviderGatewaysDetailsButtonColumn = 7,
            serviceProviderGatewaysEditButtonColumn = 9,
            serviceProviderGatewaysIdColumn = 1;

    public static final String
            serviceProviderServicesTableId = "servicesTable",
            serviceProviderServicesFirstEditButtonClassName = "fa-image",
            serviceProviderServicesDetailsButtonClassName = "fa-search-plus",
            serviceProviderServicesSecondEditButtonClassName = "fa-pencil-square-o";
    public static final int
            serviceProviderServicesIdColumn = 2,
            serviceProviderServicesNameColumn = 0,
            serviceProviderServicesTrashButtonColumn = 6,
            serviceProviderServicesDefaultNameColumn = 0,
            serviceProviderServicesFirstEditButtonColumn = 6,
            serviceProviderServicesDetailsButtonColumn = 6,
            serviceProviderServicesSecondEditButtonColumn = 6;

    public static final String
            serviceConsumerApiKeysTableId = "apiKeyTable";
    public static final int
            serviceConsumerApiKeysTrashButtonColumn = 6, //Will be usable when #56 PR is merged.
            serviceConsumerApiKeysDefaultNameColumn = 3;

    public static final String
            serviceConsumerServicesTableId = "servicesTable";
    //There is no deletion button for service in consumer services table, yet. However, this parameter is required by TableUtils.
    public static final int
            serviceConsumerServicesTrashButtonColumn = 0,
            serviceConsumerServicesDefaultNameColumn = 0,
            serviceConsumerServicesApiKeyGenerationColumn = 4;

}
