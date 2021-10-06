package eu.coatrack.admin.selenium.configuration;

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

import static eu.coatrack.admin.selenium.configuration.PageConfiguration.startpageUrl;

//TODO This configuration could be simplified and the API can be made more robust, when the TableUtils automatically
// searches for the right column for a button. Is that a good idea?
public class TableConfiguration {

    public static final String emptyTableText = "No items yet";
    public static final String trashButtonClassName = "fa-trash";
    public static final String uiDialogClassName = "ui-dialog";

    public static final String  serviceProviderApiKeysTableId = "apiKeyTable";
    public static final int     serviceProviderApiKeysTrashButtonColumn = 6;
    public static final int     serviceProviderApiKeysDefaultNameColumn = 2;
    public static final int     serviceProviderApiKeysCalenderButtonColumn = 5;
    public static final int     serviceProviderApiKeysDetailsButtonColumn = 6;
    public static final int     serviceProviderApiKeysEditButtonColumn = 6;
    public static final String  serviceProviderApiKeysCalenderButtonClassName = "glyphicon-calendar";
    public static final String  serviceProviderApiKeysDetailsButtonClassName = "fa-search-plus";
    public static final String  serviceProviderApiKeysEditButtonClassName = "fa-pencil-square-o";

    public static final String  serviceProviderGatewaysTableId = "proxiesTable";
    public static final int     serviceProviderGatewaysTrashButtonColumn = startpageUrl.contains("https://coatrack.eu") ? 8 : 9; //TODO To be adapted when coatrack.eu merges the Gateway Health Monitor feature.
    public static final int     serviceProviderGatewaysNameColumn = 0;
    public static final int     serviceProviderGatewaysDetailsButtonColumn = 7;
    public static final int     serviceProviderGatewaysEditButtonColumn = startpageUrl.contains("https://coatrack.eu") ? 8 : 9; //TODO To be adapted when coatrack.eu merges the Gateway Health Monitor feature.
    public static final int     serviceProviderGatewaysIdColumn = 1;
    public static final String  serviceProviderGatewaysDetailsButtonClassName = "fa-bar-chart";
    public static final String  serviceProviderGatewaysEditButtonClassName = "fa-pencil-square-o";

    public static final String  serviceProviderServicesTableId = "servicesTable";
    public static final int     serviceProviderServicesIdColumn = 2;
    public static final int     serviceProviderServicesNameColumn = 0;
    public static final int     serviceProviderServicesTrashButtonColumn = 6;
    public static final int     serviceProviderServicesDefaultNameColumn = 0;
    public static final int     serviceProviderServicesFirstEditButtonColumn = 6;
    public static final int     serviceProviderServicesDetailsButtonColumn = 6;
    public static final int     serviceProviderServicesSecondEditButtonColumn = 6;
    public static final String  serviceProviderServicesFirstEditButtonClassName = "fa-image";
    public static final String  serviceProviderServicesDetailsButtonClassName = "fa-search-plus";
    public static final String  serviceProviderServicesSecondEditButtonClassName = "fa-pencil-square-o";

    public static final String  serviceConsumerApiKeysTableId = "apiKeyTable";
    public static final int     serviceConsumerApiKeysTrashButtonColumn = 6; //Will be usable when #56 PR is merged.
    public static final int     serviceConsumerApiKeysDefaultNameColumn = 3;

    public static final String  serviceConsumerServicesTableId = "servicesTable";
    //There is no deletion button for service in consumer services table, yet. However, this parameter is required by TableUtils.
    public static final int     serviceConsumerServicesTrashButtonColumn = 0;
    public static final int     serviceConsumerServicesDefaultNameColumn = 0;
    public static final int     serviceConsumerServicesApiKeyGenerationColumn = 4;

}
