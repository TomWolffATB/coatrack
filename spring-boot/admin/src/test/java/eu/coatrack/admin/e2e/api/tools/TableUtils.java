package eu.coatrack.admin.e2e.api.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static eu.coatrack.admin.e2e.api.tools.TableDetails.createTableDetailsFromTableType;
import static eu.coatrack.admin.e2e.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.e2e.configuration.TableConfiguration.*;

public class TableUtils {

    private final WebDriver driver;
    private final WaiterUtils waiterUtils;
    private final TableDetails tableDetails;

    public TableUtils(WebDriver driver, TableType tableType) {
        this.driver = driver;
        waiterUtils = new WaiterUtils(driver);
        tableDetails = createTableDetailsFromTableType(tableType);
    }

    public void deleteItem(String itemName) {
        ensureDriverToBeAtCorrectTargetUrl();
        WebElement rowOfItemToBeDeleted = getRowByItemName(itemName);
        deleteRow(rowOfItemToBeDeleted);
    }

    private void ensureDriverToBeAtCorrectTargetUrl(){
        if (!driver.getCurrentUrl().equals(tableDetails.tableUrl))
            driver.get(tableDetails.tableUrl);
    }

    private void deleteRow(WebElement row) {
        WebElement cellWithTrashButton = getCellInColumn(row, tableDetails.trashButtonColumn);
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
        waiterUtils.waitForElementWithId(tableDetails.tableId);
        WebElement itemTable = driver.findElement(By.id(tableDetails.tableId));
        List<WebElement> rows = itemTable.findElements(By.cssSelector("tr"));
        rows.remove(0); //Removes the table header.
        return rows;
    }

    public boolean isItemWithinList(String itemName) {
        ensureDriverToBeAtCorrectTargetUrl();
        List<WebElement> rows = getItemRows();
        if (rows.isEmpty() || getCellInColumn(rows.get(0), 0).getText().contains(emptyTableText))
            return false;
        List<String> listOfItemNames = rows.stream().map(row -> getCellInColumn(row, tableDetails.defaultNameColumn)
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
        return getRowByCustomIdentifier(itemName, tableDetails.defaultNameColumn);
    }

    private WebElement getRowByCustomIdentifier(String identifier, int identifierColumn) {
        return getItemRows().stream().filter(row -> row.findElements(By.cssSelector("td")).get(identifierColumn).getText().contains(identifier)).findFirst().get();
    }

    public String getColumnTextFromItemRow(String identifier, int identifierColumn, int targetColumn){
        WebElement row = getRowByCustomIdentifier(identifier, identifierColumn);
        return getCellInColumn(row, targetColumn).getText();
    }

}
