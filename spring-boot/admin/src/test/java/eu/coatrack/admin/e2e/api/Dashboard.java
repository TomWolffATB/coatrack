package eu.coatrack.admin.e2e.api;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

import static eu.coatrack.admin.e2e.PageProvider.pathPrefix;

public class Dashboard {

    private final WebDriver driver;

    private String gatewayDownloadLink;
    private String serviceName;
    private String apiKeyValue;

    public Dashboard(WebDriver driver) {
        this.driver = driver;
    }

    public Dashboard createItemsViaTutorial(){
        driver.get(pathPrefix + "/admin/gettingstarted");

        driver.findElement(By.linkText("Tutorial")).click();
        driver.findElement(By.linkText("Next")).click();

        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(By.id("serviceName")));
        driver.findElement(By.id("serviceName")).click();

        Random random = new Random();
        serviceName = "my-service" + random.nextInt();

        driver.findElement(By.id("serviceName")).sendKeys(serviceName);
        driver.findElement(By.linkText("Next")).click();
        driver.findElement(By.id("serviceUrl")).click();
        driver.findElement(By.id("serviceUrl")).sendKeys("https://www.bing.com");
        driver.findElement(By.linkText("Next")).click();
        driver.findElement(By.id("serviceForFreeYes")).click();
        driver.findElement(By.linkText("Next")).click();
        driver.findElement(By.linkText("Finish")).click();

        WebElement gatewayDownloadLinkElement = new WebDriverWait(driver, 60)
                .until(ExpectedConditions.presenceOfElementLocated(By.linkText("Click here to download your CoatRack Gateway")));

        gatewayDownloadLink = gatewayDownloadLinkElement.getAttribute("href");
        apiKeyValue = driver.findElement(By.cssSelector(".row:nth-child(3) p:nth-child(2)")).getText();

        return this;
    }

    public String getGatewayDownloadLink() {
        return gatewayDownloadLink;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getApiKeyValue() {
        return apiKeyValue;
    }

}