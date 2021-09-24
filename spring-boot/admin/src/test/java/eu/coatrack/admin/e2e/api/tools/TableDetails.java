package eu.coatrack.admin.e2e.api.tools;

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
            tableId = adminConsumerServicesTableId;
            tableUrl = consumerServiceListUrl;
            trashButtonColumn = adminConsumerServicesTrashButtonColumn; //Not present
            defaultNameColumn = adminConsumerServicesDefaultNameColumn;
        } else if (tableType == TableType.CONSUMER_APIKEY_TABLE) {
            tableId = adminConsumerApiKeysTableId;
            tableUrl = consumerApiKeyListUrl;
            trashButtonColumn = adminConsumerApiKeysTrashButtonColumn;
            defaultNameColumn = adminConsumerApiKeysDefaultNameColumn;
        } else {
            throw new UndefinedTableTypeException("Please implement the table type details here.");
        }

        return new TableDetails(tableId, tableUrl, trashButtonColumn, defaultNameColumn);
    }

}
