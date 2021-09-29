package eu.coatrack.admin.e2e.api.tools;

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

import eu.coatrack.admin.e2e.exceptions.UndefinedTableTypeException;

import static eu.coatrack.admin.e2e.configuration.PageConfiguration.*;
import static eu.coatrack.admin.e2e.configuration.TableConfiguration.*;

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
            tableId = adminServicesTableId;
            tableUrl = adminServiceListUrl;
            trashButtonColumn = adminServicesTrashButtonColumn;
            defaultNameColumn = adminServicesDefaultNameColumn;
        } else if (tableType == TableType.GATEWAY_TABLE) {
            tableId = adminGatewaysTableId;
            tableUrl = adminGatewayListUrl;
            trashButtonColumn = adminGatewaysTrashButtonColumn;
            defaultNameColumn = adminGatewaysDefaultNameColumn;
        } else if (tableType == TableType.APIKEY_TABLE) {
            tableId = adminApiKeysTableId;
            tableUrl = adminApiKeyListUrl;
            trashButtonColumn = adminApiKeysTrashButtonColumn;
            defaultNameColumn = adminApiKeysDefaultNameColumn;
        } else if (tableType == TableType.CONSUMER_SERVICE_TABLE) {
            tableId = consumerServicesTableId;
            tableUrl = consumerServiceListUrl;
            trashButtonColumn = consumerServicesTrashButtonColumn; //Not present
            defaultNameColumn = consumerServicesDefaultNameColumn;
        } else if (tableType == TableType.CONSUMER_APIKEY_TABLE) {
            tableId = consumerApiKeysTableId;
            tableUrl = consumerApiKeyListUrl;
            trashButtonColumn = consumerApiKeysTrashButtonColumn;
            defaultNameColumn = consumerApiKeysDefaultNameColumn;
        } else {
            throw new UndefinedTableTypeException("Please implement the table type details here.");
        }

        return new TableDetails(tableId, tableUrl, trashButtonColumn, defaultNameColumn);
    }

}
