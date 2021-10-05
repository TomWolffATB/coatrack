package eu.coatrack.admin.selenium.api.tools.table;

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

import eu.coatrack.admin.selenium.exceptions.UndefinedTableTypeException;

import static eu.coatrack.admin.selenium.configuration.PageConfiguration.*;
import static eu.coatrack.admin.selenium.configuration.TableConfiguration.*;

public class TableDetails {

    public final String tableId;
    public final String tableUrl;
    public final int trashButtonColumn;
    public final int defaultNameColumn;

    public TableDetails(String tableId, String tableUrl, int trashButtonColumn, int defaultNameColumn) {
        this.tableId = tableId;
        this.tableUrl = tableUrl;
        this.trashButtonColumn = trashButtonColumn;
        this.defaultNameColumn = defaultNameColumn;
    }

    public static TableDetails createTableDetailsFromTableType(TableType tableType) {
        String tableId;
        String tableUrl;
        int trashButtonColumn;
        int defaultNameColumn;

        if (tableType == TableType.SERVICE_TABLE){
            tableId = serviceProviderServicesTableId;
            tableUrl = serviceProviderServicesUrl;
            trashButtonColumn = serviceProviderServicesTrashButtonColumn;
            defaultNameColumn = serviceProviderServicesDefaultNameColumn;
        } else if (tableType == TableType.GATEWAY_TABLE) {
            tableId = serviceProviderGatewaysTableId;
            tableUrl = serviceProviderGatewaysUrl;
            trashButtonColumn = serviceProviderGatewaysTrashButtonColumn;
            defaultNameColumn = serviceProviderGatewaysNameColumn;
        } else if (tableType == TableType.APIKEY_TABLE) {
            tableId = serviceProviderApiKeysTableId;
            tableUrl = serviceProviderApiKeysUrl;
            trashButtonColumn = serviceProviderApiKeysTrashButtonColumn;
            defaultNameColumn = serviceProviderApiKeysDefaultNameColumn;
        } else if (tableType == TableType.CONSUMER_SERVICE_TABLE) {
            tableId = serviceConsumerServicesTableId;
            tableUrl = serviceConsumerServiceListUrl;
            trashButtonColumn = serviceConsumerServicesTrashButtonColumn; //Not present
            defaultNameColumn = serviceConsumerServicesDefaultNameColumn;
        } else if (tableType == TableType.CONSUMER_APIKEY_TABLE) {
            tableId = serviceConsumerApiKeysTableId;
            tableUrl = serviceConsumerApiKeyListUrl;
            trashButtonColumn = serviceConsumerApiKeysTrashButtonColumn;
            defaultNameColumn = serviceConsumerApiKeysDefaultNameColumn;
        } else {
            throw new UndefinedTableTypeException("Please implement the table type details here.");
        }

        return new TableDetails(tableId, tableUrl, trashButtonColumn, defaultNameColumn);
    }

}
