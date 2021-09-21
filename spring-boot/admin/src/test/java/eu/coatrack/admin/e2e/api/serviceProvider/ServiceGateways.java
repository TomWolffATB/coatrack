package eu.coatrack.admin.e2e.api.serviceProvider;

import eu.coatrack.admin.e2e.api.tools.TableUtils;
import eu.coatrack.admin.e2e.api.tools.WaiterUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static eu.coatrack.admin.e2e.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getAdminGatewaysPage;
import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getAdminServicesPage;

public class ServiceGateways {

    private final WebDriver driver;
    private final WaiterUtils waiterUtils;
    private final TableUtils gatewayTableUtils;

    public ServiceGateways(WebDriver driver) {
        this.driver = driver;
        waiterUtils = new WaiterUtils(driver);
        gatewayTableUtils = new TableUtils(driver, "proxiesTable", getAdminGatewaysPage());
    }

    public String createGateway() {
        driver.get(getAdminGatewaysPage());

        driver.findElement(By.linkText("Create Service Gateway")).click();
        driver.findElement(By.id("name")).click();

        String gatewayName = "my-gateway-" + (new Random().nextInt());

        driver.findElement(By.id("name")).sendKeys(gatewayName);
        driver.findElement(By.id("proxyPublicUrlInputField")).click();
        driver.findElement(By.id("proxyPublicUrlInputField")).sendKeys("https://mysite.com:8080");
        driver.findElement(By.id("port")).click();
        driver.findElement(By.id("port")).sendKeys("8080");
        driver.findElement(By.name("selectedServices")).click();
        driver.findElement(By.cssSelector(".checkbox:nth-child(2) .flat")).click();
        driver.findElement(By.id("description")).click();
        driver.findElement(By.id("description")).sendKeys("some Description");
        driver.findElement(By.id("save")).click();

        //TODO To be abstracted.
        waiterUtils.waitUpToAMinuteForElementWithId("proxiesTable");

        return gatewayName;
    }

    public boolean isGatewayWithinList(String gatewayName) {
        driver.get(getAdminGatewaysPage());
        List<WebElement> rows = gatewayTableUtils.getItemRows();
        List<String> listOfGatewayNames = rows.stream().map(row -> row.findElement(By.cssSelector("td"))
                .getText()).collect(Collectors.toList());
        driver.navigate().refresh();
        return listOfGatewayNames.contains(gatewayName);
    }

    public void deleteGateway(String gatewayName) {
        gatewayTableUtils.deleteItem(gatewayName, 8);
    }
}
