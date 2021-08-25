package eu.coatrack.admin.e2e.api.ServiceOfferingsSetup;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static eu.coatrack.admin.e2e.PageFactory.pathPrefix;

public class ServiceOfferings {

    private final WebDriver driver;

    public ServiceOfferings(WebDriver driver) {
        this.driver = driver;
    }

    public String createService() {
        driver.get(pathPrefix + "/admin");

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

        try {
            Thread.sleep(1000);
        } catch (Exception ignored){}
        driver.findElement(By.linkText("Finish")).sendKeys(Keys.RETURN);

        return serviceName;
    }

    public boolean isServiceWithinList(String serviceName) {
        driver.get(pathPrefix + "/admin/services");
        List<WebElement> rows = getServicesRows();
        List<String> listOfServiceNames = rows.stream().map(row -> row.findElement(By.cssSelector("td")).getText()).collect(Collectors.toList());
        driver.navigate().refresh();
        return listOfServiceNames.contains(serviceName);
    }

    private void waitForElementWithId(String id) {
        new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    public void deleteService(String serviceName) {
        driver.get(pathPrefix + "/admin/services");

        List<WebElement> rows = getServicesRows();
        WebElement rowOfDesiredService = rows.stream().filter(row -> row.findElement(By.cssSelector("td")).getText().equals(serviceName)).findFirst().get();
        List<WebElement> listOfRowElements = rowOfDesiredService.findElements(By.cssSelector("td"));

        WebElement cellWithTrashButton = listOfRowElements.get(6);
        WebElement trashButton = cellWithTrashButton.findElements(By.cssSelector("i")).get(3);
        trashButton.click();

        try {
            Thread.sleep(2000);
        } catch (Exception ignored) {}
        WebElement infoDialog = driver.findElement(By.className("ui-dialog"));
        List<WebElement> listOfButtons = infoDialog.findElements(By.cssSelector("button"));
        WebElement yesButton = listOfButtons.stream().filter(button -> button.getText().contains("Yes")).findFirst().get();
        yesButton.click();
    }

    private List<WebElement> getServicesRows() {
        waitForElementWithId("servicesTable");
        WebElement servicesTable = driver.findElement(By.id("servicesTable"));
        List<WebElement> rows = servicesTable.findElements(By.cssSelector("tr"));
        rows.remove(0);
        return rows;
    }
}
