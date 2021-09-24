package eu.coatrack.admin.e2e.api.tools;

import eu.coatrack.admin.e2e.exceptions.UndefinedTableTypeException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static eu.coatrack.admin.e2e.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.e2e.configuration.TestConfiguration.*;

public class TableUtils {

    private final WebDriver driver;
    private final WaiterUtils waiterUtils;

    private String tableId;
    private String tableUrl;
    private int trashButtonColumn;
    private int defaultNameColumn;

    public TableUtils(WebDriver driver, TableType tableType) {
        this.driver = driver;
        waiterUtils = new WaiterUtils(driver);
        initTableFieldsAccordingToTableType(tableType);
    }

    private void initTableFieldsAccordingToTableType(TableType tableType) {
        if (tableType == TableType.SERVICE_TABLE){
            tableId = "servicesTable";
            tableUrl = getAdminServiceListUrl();
            trashButtonColumn = 6;
            defaultNameColumn = 0;
        } else if (tableType == TableType.GATEWAY_TABLE) {
            tableId = "proxiesTable";
            tableUrl = getAdminGatewayListUrl();
            trashButtonColumn = 8;
            defaultNameColumn = 0;
        } else if (tableType == TableType.APIKEY_TABLE) {
            tableId = "apiKeyTable";
            tableUrl = getAdminApiKeyListUrl();
            trashButtonColumn = 6;
            defaultNameColumn = 2;
        } else if (tableType == TableType.CONSUMER_SERVICE_TABLE) {
            tableId = "servicesTable";
            tableUrl = getConsumerServiceListUrl();
            trashButtonColumn = 0; //Not present
            defaultNameColumn = 0;
        } else if (tableType == TableType.CONSUMER_APIKEY_TABLE) {
            tableId = "apiKeyTable";
            tableUrl = getConsumerApiKeyListUrl();
            trashButtonColumn = 6;
            defaultNameColumn = 3;
        } else {
            throw new UndefinedTableTypeException("Please implement the table type details here.");
        }
    }

    public void deleteItem(String itemName) {
        ensureDriverToBeAtCorrectTargetUrl();
        WebElement rowOfItemToBeDeleted = getRowByItemName(itemName);
        deleteRow(rowOfItemToBeDeleted);
    }

    private void ensureDriverToBeAtCorrectTargetUrl(){
        if (!driver.getCurrentUrl().equals(tableUrl))
            driver.get(tableUrl);
    }

    private void deleteRow(WebElement row) {
        WebElement cellWithTrashButton = getCellInColumn(row, trashButtonColumn);
        WebElement trashButton = cellWithTrashButton.findElement(By.className("fa-trash"));
        trashButton.click();

        sleepMillis(2000);
        WebElement infoDialog = driver.findElement(By.className("ui-dialog"));
        List<WebElement> listOfButtons = infoDialog.findElements(By.cssSelector("button"));
        WebElement yesButton = listOfButtons.stream().filter(button -> button.getText().contains("Yes")).findFirst().get();
        yesButton.click();
    }

    private WebElement getCellInColumn(WebElement row, int column){
        return row.findElements(By.cssSelector("td")).get(column);
    }

    public List<String> getListOfColumnValues(int column){
        ensureDriverToBeAtCorrectTargetUrl();
        return getItemRows().stream()
                .filter(row -> !getCellInColumn(row, 0).getText().contains("No items yet"))
                .map(row -> getCellInColumn(row, column).getText()).collect(Collectors.toList());
    }

    private List<WebElement> getItemRows() {
        ensureDriverToBeAtCorrectTargetUrl();
        waiterUtils.waitForElementWithId(tableId);
        WebElement itemTable = driver.findElement(By.id(tableId));
        List<WebElement> rows = itemTable.findElements(By.cssSelector("tr"));
        rows.remove(0); //Removes the table header.
        return rows;
    }

    public boolean isItemWithinList(String itemName) {
        ensureDriverToBeAtCorrectTargetUrl();
        List<WebElement> rows = getItemRows();
        List<String> listOfItemNames = rows.stream().map(row -> getCellInColumn(row, defaultNameColumn)
                .getText()).collect(Collectors.toList());
        driver.navigate().refresh();
        return listOfItemNames.contains(itemName);
    }

    public void deleteAllItem(){
        ensureDriverToBeAtCorrectTargetUrl();

        List<WebElement> rows = getItemRows();
        while (true){
            WebElement firstTableBodyRow = rows.get(0);
            String contentOfFirstCell = firstTableBodyRow.findElement(By.cssSelector("td")).getText();

            if (contentOfFirstCell.contains("No items yet"))
                break;
            else {
                deleteRow(firstTableBodyRow);
                driver.navigate().refresh();
                rows = getItemRows();
            }
        }
    }

    //TODO All further configuration like column indices should be retrieved from one central config location.
    //TODO The whole configuration should be soft-coded.
    //TODO This methods name seems not to be abstract enough. Eventually put it in ServiceOffering class.
    public void createApiKeyFromPublicService(String serviceName) {
        ensureDriverToBeAtCorrectTargetUrl();
        WebElement rowOfService = getItemRows().stream().filter(row -> getCellInColumn(row, 0).getText().contains(serviceName)).findFirst().get();
        getCellInColumn(rowOfService, 4).findElement(By.cssSelector("button")).click();
    }

    public void clickOnButton(String itemName, int columnContainingButton, String buttonClassName) {
        WebElement row = getRowByItemName(itemName);
        getCellInColumn(row, columnContainingButton).findElements(By.cssSelector("button")).stream().filter(button -> button.findElements(By.className(buttonClassName)).size() > 0).findFirst().get().click();
    }

    private WebElement getRowByItemName(String itemName) {
        return getRowByCustomIdentifier(itemName, defaultNameColumn);
    }

    private WebElement getRowByCustomIdentifier(String identifier, int identifierColumn) {
        return getItemRows().stream().filter(row -> row.findElements(By.cssSelector("td")).get(identifierColumn).getText().contains(identifier)).findFirst().get();
    }

    public String getInfo(String identifier, int identifierColumn, int targetColumn){
        WebElement row = getRowByCustomIdentifier(identifier, identifierColumn);
        return getCellInColumn(row, targetColumn).getText();
    }

}
