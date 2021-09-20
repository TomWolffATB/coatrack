package eu.coatrack.admin.e2e.api;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getAdminTutorialPage;

public class Tutorial {

    private final WebDriver driver;

    private String gatewayDownloadLink;
    private String serviceName;
    private String apiKeyValue;

    public Tutorial(WebDriver driver) {
        this.driver = driver;
    }

    public Tutorial createItemsViaTutorial(){
        driver.get(getAdminTutorialPage());

        driver.findElement(By.linkText("Tutorial")).click();
        driver.findElement(By.linkText("Next")).sendKeys(Keys.RETURN);

        waitForElementWithId("serviceName");
        driver.findElement(By.id("serviceName")).click();

        serviceName = "my-service" + new Random().nextInt();

        driver.findElement(By.id("serviceName")).sendKeys(serviceName);
        driver.findElement(By.linkText("Next")).sendKeys(Keys.RETURN);

        waitForElementWithId("serviceUrl");
        driver.findElement(By.id("serviceUrl")).click();
        driver.findElement(By.id("serviceUrl")).sendKeys("https://www.bing.com");
        driver.findElement(By.linkText("Next")).sendKeys(Keys.RETURN);

        waitForElementWithId("serviceForFreeYes");
        driver.findElement(By.id("serviceForFreeYes")).click();
        driver.findElement(By.linkText("Next")).sendKeys(Keys.RETURN);

        try {
            Thread.sleep(1000);
        } catch (Exception ignored){}
        driver.findElement(By.linkText("Finish")).sendKeys(Keys.RETURN);

        WebElement gatewayDownloadLinkElement = new WebDriverWait(driver, 60)
                .until(ExpectedConditions.presenceOfElementLocated(By.linkText("Click here to download your CoatRack Gateway")));

        gatewayDownloadLink = gatewayDownloadLinkElement.getAttribute("href");
        apiKeyValue = driver.findElement(By.cssSelector(".row:nth-child(3) p:nth-child(2)")).getText();

        return this;
    }

    private void waitForElementWithId(String id) {
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.id(id)));
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