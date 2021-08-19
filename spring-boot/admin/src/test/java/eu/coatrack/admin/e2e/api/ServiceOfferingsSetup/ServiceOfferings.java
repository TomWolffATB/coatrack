package eu.coatrack.admin.e2e.api.ServiceOfferingsSetup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static eu.coatrack.admin.e2e.PageProvider.pathPrefix;

public class ServiceOfferings {

    private final WebDriver driver;

    public ServiceOfferings(WebDriver driver) {
        this.driver = driver;
    }

    public ServiceOfferings createService() {
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

        driver.findElement(By.linkText("Next")).click();
        driver.findElement(By.linkText("Next")).click();

        driver.findElement(By.id("freeButton")).click();
        driver.findElement(By.linkText("Next")).click();
        driver.findElement(By.linkText("Finish")).click();

        /*
        driver.findElement(By.cssSelector("#item13 > .sorting_1")).click();
        driver.findElement(By.cssSelector("#item13 > td:nth-child(2)")).click();
        driver.findElement(By.cssSelector("#item13 > td:nth-child(3)")).click();
        driver.findElement(By.cssSelector("#item13 > td:nth-child(4)")).click();
        driver.findElement(By.cssSelector("#item13 > td:nth-child(5)")).click();
        driver.findElement(By.cssSelector("#item13 > td:nth-child(6)")).click();
        driver.findElement(By.cssSelector("#item13 .fa-image")).click();
        driver.findElement(By.cssSelector("#item13 .fa-search-plus")).click();
        driver.findElement(By.cssSelector("#item13 .fa-pencil-square-o")).click();
        driver.findElement(By.cssSelector("#item13 button:nth-child(4)")).click();
        {
            WebElement element = driver.findElement(By.cssSelector("#item13 button:nth-child(4)"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
        driver.findElement(By.cssSelector(".ui-dialog-buttonset > button:nth-child(1)")).click();
        */
        return this;
    }
}
