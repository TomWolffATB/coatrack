package eu.coatrack.admin.e2e.configuration;

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

public class TableConfiguration {

    public static final String emptyTableText = "No items yet";
    public static final String trashButtonClassName = "fa-trash";
    public static final String uiDialogClassName = "ui-dialog";

    public static final String adminApiKeysTableId = "apiKeyTable";
    public static final int adminApiKeysTrashButtonColumn = 6;
    public static final int adminApiKeysDefaultNameColumn = 2;
    public static final int adminApiKeysCalenderButtonColumn = 5;
    public static final int adminApiKeysDetailsButtonColumn = 6;
    public static final int adminApiKeysEditButtonColumn = 6;
    public static final String adminApiKeysCalenderButtonClassName = "glyphicon-calendar";
    public static final String adminApiKeysDetailsButtonClassName = "fa-search-plus";
    public static final String adminApiKeysEditButtonClassName = "fa-pencil-square-o";

    public static final String adminGatewaysTableId = "proxiesTable";
    public static final int adminGatewaysTrashButtonColumn = 9;
    public static final int adminGatewaysNameColumn = 0;
    public static final int adminGatewaysDetailsButtonColumn = 7;
    public static final int adminGatewaysEditButtonColumn = 9;
    public static final int adminGatewaysIdColumn = 1;
    public static final String adminGatewaysDetailsButtonClassName = "fa-bar-chart";
    public static final String adminGatewaysEditButtonClassName = "fa-pencil-square-o";

    public static final String adminServicesTableId = "servicesTable";
    public static final int adminServicesIdColumn = 2;
    public static final int adminServicesNameColumn = 0;
    public static final int adminServicesTrashButtonColumn = 6;
    public static final int adminServicesDefaultNameColumn = 0;
    public static final int adminServicesFirstEditButtonColumn = 6;
    public static final int adminServicesDetailsButtonColumn = 6;
    public static final int adminServicesSecondEditButtonColumn = 6;
    public static final String adminServicesFirstEditButtonClassName = "fa-image";
    public static final String adminServicesDetailsButtonClassName = "fa-search-plus";
    public static final String adminServicesSecondEditButtonClassName = "fa-pencil-square-o";

    public static final String consumerApiKeysTableId = "apiKeyTable";
    public static final int consumerApiKeysTrashButtonColumn = 6; //Will be usable when #56 PR is merged.
    public static final int consumerApiKeysDefaultNameColumn = 3;

    public static final String consumerServicesTableId = "servicesTable";
    //There is no deletion button for service in consumer services table, yet. However, this parameter is required by TableUtils.
    public static final int consumerServicesTrashButtonColumn = 0;
    public static final int consumerServicesDefaultNameColumn = 0;
    public static final int consumerServicesApiKeyGenerationColumn = 4;

}
