package eu.coatrack.admin.e2e.configuration;

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
    public static final int adminGatewaysTrashButtonColumn = 8;
    public static final int adminGatewaysDefaultNameColumn = 0;
    public static final int adminGatewaysDetailsButtonColumn = 7;
    public static final int adminGatewaysEditButtonColumn = 8;
    public static final int adminGatewaysIdColumn = 1;
    public static final String adminGatewaysDetailsButtonClassName = "fa-bar-chart";
    public static final String adminGatewaysEditButtonClassName = "fa-pencil-square-o";

    public static final String adminServicesTableId = "servicesTable";
    public static final int adminServicesTrashButtonColumn = 6;
    public static final int adminServicesDefaultNameColumn = 0;
    public static final int adminServicesFirstEditButtonColumn = 6;
    public static final int adminServicesDetailsButtonColumn = 6;
    public static final int adminServicesSecondEditButtonColumn = 6;
    public static final String adminServicesFirstEditButtonClassName = "fa-image";
    public static final String adminServicesDetailsButtonClassName = "fa-search-plus";
    public static final String adminServicesSecondEditButtonClassName = "fa-pencil-square-o";

    public static final String adminConsumerApiKeysTableId = "apiKeyTable";
    public static final int adminConsumerApiKeysTrashButtonColumn = 6; //Will be usable when #56 PR is merged.
    public static final int adminConsumerApiKeysDefaultNameColumn = 3;

    public static final String adminConsumerServicesTableId = "servicesTable";
    public static final int adminConsumerServicesTrashButtonColumn = 0; //Not present
    public static final int adminConsumerServicesDefaultNameColumn = 0;

}
