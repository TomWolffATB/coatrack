package eu.coatrack.admin.e2e.api.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static eu.coatrack.admin.e2e.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.e2e.api.tools.WaiterUtils.waitForElementWithId;
import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getAdminServicesPage;

public class TableUtils {

    private final WebDriver driver;
    private final String tableId;

    public TableUtils(WebDriver driver, String tableId) {
        this.driver = driver;
        this.tableId = tableId;
    }

    //TODO When I command the application to delete something and the wring page is opened, the test will fail.
    //TODO This could be prevented by a simple check: If current page is correct, then continue. Else: Go to correct page and then continue.

    public void deleteServiceInRow(WebElement row) {
        List<WebElement> listOfRowElements = row.findElements(By.cssSelector("td"));
        WebElement cellWithTrashButton = listOfRowElements.get(6);
        WebElement trashButton = cellWithTrashButton.findElements(By.cssSelector("i")).get(3);
        trashButton.click();

        sleepMillis(1000);
        WebElement infoDialog = driver.findElement(By.className("ui-dialog"));
        List<WebElement> listOfButtons = infoDialog.findElements(By.cssSelector("button"));
        WebElement yesButton = listOfButtons.stream().filter(button -> button.getText().contains("Yes")).findFirst().get();
        yesButton.click();
    }

    public void deleteItem(String itemName) {
        driver.get(getAdminServicesPage());
        WebElement rowOfDesiredService = getServiceRows().stream()
                .filter(row -> row.findElement(By.cssSelector("td")).getText().equals(itemName)).findFirst().get();
        deleteServiceInRow(rowOfDesiredService);
    }

    public List<WebElement> getServiceRows() {
        waitForElementWithId(tableId, driver);
        WebElement servicesTable = driver.findElement(By.id(tableId));
        List<WebElement> rows = servicesTable.findElements(By.cssSelector("tr"));
        rows.remove(0); //Removes the table header.
        return rows;
    }

}
