package eu.coatrack.admin.e2e.api.tools;

import eu.coatrack.admin.e2e.exceptions.UndefinedTableTypeException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static eu.coatrack.admin.e2e.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.*;
import static eu.coatrack.admin.e2e.configuration.TableConfiguration.*;

public class TableUtils {

    private final WebDriver driver;
    private final WaiterUtils waiterUtils;

    //TODO Should be extracted in a object, e.g. 'TableDetails'. The initMethodLogic should be outsourced as well.
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
            tableId = adminServicesTableId;
            tableUrl = getAdminServiceListUrl();
            trashButtonColumn = adminServicesTrashButtonColumn;
            defaultNameColumn = adminServicesDefaultNameColumn;
        } else if (tableType == TableType.GATEWAY_TABLE) {
            tableId = adminGatewaysTableId;
            tableUrl = getAdminGatewayListUrl();
            trashButtonColumn = adminGatewaysTrashButtonColumn;
            defaultNameColumn = adminGatewaysDefaultNameColumn;
        } else if (tableType == TableType.APIKEY_TABLE) {
            tableId = adminApiKeysTableId;
            tableUrl = getAdminApiKeyListUrl();
            trashButtonColumn = adminApiKeysTrashButtonColumn;
            defaultNameColumn = adminApiKeysDefaultNameColumn;
        } else if (tableType == TableType.CONSUMER_SERVICE_TABLE) {
            tableId = adminConsumerServicesTableId;
            tableUrl = getConsumerServiceListUrl();
            trashButtonColumn = adminConsumerServicesTrashButtonColumn; //Not present
            defaultNameColumn = adminConsumerServicesDefaultNameColumn;
        } else if (tableType == TableType.CONSUMER_APIKEY_TABLE) {
            tableId = adminConsumerApiKeysTableId;
            tableUrl = getConsumerApiKeyListUrl();
            trashButtonColumn = adminConsumerApiKeysTrashButtonColumn;
            defaultNameColumn = adminConsumerApiKeysDefaultNameColumn;
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
        WebElement trashButton = cellWithTrashButton.findElement(By.className(trashButtonClassName));
        trashButton.click();

        sleepMillis(2000);
        WebElement infoDialog = driver.findElement(By.className(uiDialogClassName));
        List<WebElement> listOfButtons = infoDialog.findElements(By.cssSelector("button"));
        WebElement yesButton = listOfButtons.stream().filter(button -> button.getText().contains("Yes")).findFirst().get();
        yesButton.click();
    }

    public WebElement getCellInColumn(WebElement row, int column){
        return row.findElements(By.cssSelector("td")).get(column);
    }

    public List<String> getListOfColumnValues(int column){
        ensureDriverToBeAtCorrectTargetUrl();
        return getItemRows().stream()
                .filter(row -> !getCellInColumn(row, 0).getText().contains(emptyTableText))
                .map(row -> getCellInColumn(row, column).getText()).collect(Collectors.toList());
    }

    public List<WebElement> getItemRows() {
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
        if (rows.isEmpty() || getCellInColumn(rows.get(0), 0).getText().contains(emptyTableText))
            return false;
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

            if (contentOfFirstCell.contains(emptyTableText))
                break;
            else {
                deleteRow(firstTableBodyRow);
                driver.navigate().refresh();
                rows = getItemRows();
            }
        }
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

    public String getColumnTextFromItemRow(String identifier, int identifierColumn, int targetColumn){
        WebElement row = getRowByCustomIdentifier(identifier, identifierColumn);
        return getCellInColumn(row, targetColumn).getText();
    }

}
