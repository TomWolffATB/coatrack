package eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static eu.coatrack.admin.e2e.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.e2e.api.tools.WaiterUtils.waitForElementWithId;
import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getAdminServicesPage;
import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getDashboard;

public class ServiceOfferings {

    private final WebDriver driver;

    public ServiceOfferings(WebDriver driver) {
        this.driver = driver;
    }

    public String createService() {
        driver.get(getDashboard());

        driver.findElement(By.linkText("Service Offering Setup")).click();
        driver.findElement(By.linkText("Service Offerings")).click();
        driver.findElement(By.linkText("Create Service Offering")).click();
        driver.findElement(By.id("name")).click();

        String serviceName = "my-service-" + (new Random().nextInt());
        String serviceId = serviceName + "-id";
        System.out.println("Service Name: " + serviceName);
        driver.findElement(By.id("name")).sendKeys(serviceName);
        driver.findElement(By.id("localUrl")).click();
        driver.findElement(By.id("localUrl")).sendKeys("https://www.bing.com");
        driver.findElement(By.id("uriIdentifier")).click();
        driver.findElement(By.id("uriIdentifier")).sendKeys(serviceId);
        driver.findElement(By.id("description")).click();
        driver.findElement(By.id("description")).sendKeys("Some Description");

        driver.findElement(By.linkText("Next")).sendKeys(Keys.RETURN);
        driver.findElement(By.linkText("Next")).sendKeys(Keys.RETURN);

        driver.findElement(By.id("freeButton")).click();
        driver.findElement(By.linkText("Next")).sendKeys(Keys.RETURN);

        sleepMillis(1000);
        driver.findElement(By.linkText("Finish")).sendKeys(Keys.RETURN);

        sleepMillis(1000);
        return serviceName;
    }

    public boolean isServiceWithinList(String serviceName) {
        driver.get(getAdminServicesPage());
        List<WebElement> rows = getServiceRows();
        List<String> listOfServiceNames = rows.stream().map(row -> row.findElement(By.cssSelector("td"))
                .getText()).collect(Collectors.toList());
        driver.navigate().refresh();
        return listOfServiceNames.contains(serviceName);
    }

    //TODO This could maybe be abstracted when Gateways and API keys shall be deleted. It is always the same scheme: Get table, find rows, get a row, press delete button.
    public void deleteAllServices(){
        driver.get(getAdminServicesPage());

        List<WebElement> rows = getServiceRows();
        while (!rows.isEmpty()){
            WebElement row = rows.stream().findFirst().get();
            deleteServiceInRow(row);

            driver.navigate().refresh();
            rows = getServiceRows();
        }
    }

    private void deleteServiceInRow(WebElement row) {
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

    public void deleteService(String serviceName) {
        driver.get(getAdminServicesPage());
        WebElement rowOfDesiredService = getServiceRows().stream()
                .filter(row -> row.findElement(By.cssSelector("td")).getText().equals(serviceName)).findFirst().get();
        deleteServiceInRow(rowOfDesiredService);
    }

    private List<WebElement> getServiceRows() {
        waitForElementWithId("servicesTable", driver);
        WebElement servicesTable = driver.findElement(By.id("servicesTable"));
        List<WebElement> rows = servicesTable.findElements(By.cssSelector("tr"));
        rows.remove(0); //Removes the table header.
        return rows;
    }
}
