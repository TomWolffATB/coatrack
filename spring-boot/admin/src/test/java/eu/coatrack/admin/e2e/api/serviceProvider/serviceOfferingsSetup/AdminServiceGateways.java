package eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup;

import eu.coatrack.admin.e2e.api.tools.TableType;
import eu.coatrack.admin.e2e.api.tools.TableUtils;
import eu.coatrack.admin.e2e.api.tools.WaiterUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Random;

import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getAdminGatewayListUrl;

public class AdminServiceGateways {

    private final WebDriver driver;
    private final WaiterUtils waiterUtils;
    private final TableUtils gatewayTableUtils;

    public AdminServiceGateways(WebDriver driver) {
        this.driver = driver;
        waiterUtils = new WaiterUtils(driver);
        gatewayTableUtils = new TableUtils(driver, TableType.GATEWAY_TABLE);
    }

    public String createGateway() {
        driver.get(getAdminGatewayListUrl());
        String gatewayName = "my-gateway-" + (new Random().nextInt());
        workThroughGatewayCreationMenu(gatewayName);
        return gatewayName;
    }

    private void workThroughGatewayCreationMenu(String gatewayName) {
        driver.findElement(By.linkText("Create Service Gateway")).click();
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys(gatewayName);
        driver.findElement(By.id("proxyPublicUrlInputField")).click();
        driver.findElement(By.id("proxyPublicUrlInputField")).sendKeys("https://mysite.com:8080");
        driver.findElement(By.id("port")).click();
        driver.findElement(By.id("port")).sendKeys("8080");
        driver.findElement(By.id("description")).click();
        driver.findElement(By.id("description")).sendKeys("some Description");
        driver.findElement(By.id("save")).click();
        waiterUtils.waitUpToAMinuteForElementWithId("proxiesTable");
    }

    public boolean isGatewayWithinList(String gatewayName) {
        return gatewayTableUtils.isItemWithinList(gatewayName);
    }

    public void deleteGateway(String gatewayName) {
        gatewayTableUtils.deleteItem(gatewayName);
    }

    public void deleteAllGateways() {
        gatewayTableUtils.deleteAllItem();
    }

    public void clickOnDetailsButtonOfGateway(String gatewayName) {
        gatewayTableUtils.clickOnButton(gatewayName, 7, "fa-bar-chart");
    }

    public void clickOnEditButtonOfGateway(String gatewayName) {
        gatewayTableUtils.clickOnButton(gatewayName, 8, "fa-pencil-square-o");
    }

    public String getGatewayNameByIdentifier(String identifier){
        return gatewayTableUtils.getColumnTextFromItemRow(identifier, 1, 0);
    }
}
