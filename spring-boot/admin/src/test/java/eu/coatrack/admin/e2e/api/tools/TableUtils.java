package eu.coatrack.admin.e2e.api.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static eu.coatrack.admin.e2e.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getAdminGatewaysPage;
import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getAdminServicesPage;

public class TableUtils {

    private final WebDriver driver;
    private final String tableId;
    private final WaiterUtils waiterUtils;
    private final String tableUrl;

    public TableUtils(WebDriver driver, String tableId, String tableUrl) {
        this.driver = driver;
        this.tableId = tableId;
        waiterUtils = new WaiterUtils(driver);
        this.tableUrl = tableUrl;
    }

    //TODO When I command the application to delete something and the wring page is opened, the test will fail.
    //TODO This could be prevented by a simple check: If current page is correct, then continue. Else: Go to correct page and then continue.

    public void deleteServiceInRow(WebElement row, int columnOfTrashButton) {
        List<WebElement> listOfRowElements = row.findElements(By.cssSelector("td"));
        WebElement cellWithTrashButton = listOfRowElements.get(columnOfTrashButton);
        WebElement trashButton = cellWithTrashButton.findElement(By.className("fa-trash"));
        trashButton.click();

        sleepMillis(1000);
        WebElement infoDialog = driver.findElement(By.className("ui-dialog"));
        List<WebElement> listOfButtons = infoDialog.findElements(By.cssSelector("button"));
        WebElement yesButton = listOfButtons.stream().filter(button -> button.getText().contains("Yes")).findFirst().get();
        yesButton.click();
    }

    public void deleteItem(String itemName, int columnOfTrashButton) {
        driver.get(tableUrl);
        WebElement rowOfDesiredService = getItemRows().stream()
                .filter(row -> row.findElement(By.cssSelector("td")).getText().equals(itemName)).findFirst().get();
        deleteServiceInRow(rowOfDesiredService, columnOfTrashButton);
    }

    public List<WebElement> getItemRows() {
        waiterUtils.waitForElementWithId(tableId);
        WebElement servicesTable = driver.findElement(By.id(tableId));
        List<WebElement> rows = servicesTable.findElements(By.cssSelector("tr"));
        rows.remove(0); //Removes the table header.
        return rows;
    }

}
