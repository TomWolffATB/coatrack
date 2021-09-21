package eu.coatrack.admin.e2e.api.tools;

import eu.coatrack.admin.e2e.api.TableType;
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
    private int trashButtonColumnIndex;

    public TableUtils(WebDriver driver, TableType tableType) {
        this.driver = driver;
        waiterUtils = new WaiterUtils(driver);
        initTableFieldsAccordingToTableType(tableType);
    }

    private void initTableFieldsAccordingToTableType(TableType tableType) {
        if (tableType == TableType.SERVICE_TABLE){
            tableId = "servicesTable";
            tableUrl = getAdminServicesPage();
            trashButtonColumnIndex = 6;
        } else if (tableType == TableType.GATEWAY_TABLE) {
            tableId = "proxiesTable";
            tableUrl = getAdminGatewaysPage();
            trashButtonColumnIndex = 8;
        } else if (tableType == TableType.APIKEY_TABLE) {
            tableId = "apiKeyTable";
            tableUrl = getAdminApiKeysPage();
            trashButtonColumnIndex = 6;
        }
    }

    //TODO When I command the application to delete something and the wring page is opened, the test will fail.
    //TODO This could be prevented by a simple check: If current page is correct, then continue. Else: Go to correct page and then continue.

    private void deleteRow(WebElement row) {
        List<WebElement> listOfCellsInTheRow = row.findElements(By.cssSelector("td"));
        WebElement cellWithTrashButton = listOfCellsInTheRow.get(trashButtonColumnIndex);
        WebElement trashButton = cellWithTrashButton.findElement(By.className("fa-trash"));
        trashButton.click();

        sleepMillis(1000);
        WebElement infoDialog = driver.findElement(By.className("ui-dialog"));
        List<WebElement> listOfButtons = infoDialog.findElements(By.cssSelector("button"));
        WebElement yesButton = listOfButtons.stream().filter(button -> button.getText().contains("Yes")).findFirst().get();
        yesButton.click();
    }

    public void deleteItem(String itemName) {
        driver.get(tableUrl);
        WebElement rowOfItemToBeDeleted = getItemRows().stream()
                .filter(row -> row.findElement(By.cssSelector("td")).getText().equals(itemName)).findFirst().get();
        deleteRow(rowOfItemToBeDeleted);
    }

    private List<WebElement> getItemRows() {
        waiterUtils.waitForElementWithId(tableId);
        WebElement itemTable = driver.findElement(By.id(tableId));
        List<WebElement> rows = itemTable.findElements(By.cssSelector("tr"));
        rows.remove(0); //Removes the table header.
        return rows;
    }

    public boolean isItemWithinList(String itemName) {
        driver.get(tableUrl);
        List<WebElement> rows = getItemRows();
        List<String> listOfItemNames = rows.stream().map(row -> row.findElement(By.cssSelector("td"))
                .getText()).collect(Collectors.toList());
        driver.navigate().refresh();
        return listOfItemNames.contains(itemName);
    }

    public void deleteAllItem(){
        driver.get(tableUrl);

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

}
