package eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup;

import eu.coatrack.admin.e2e.api.TableType;
import eu.coatrack.admin.e2e.api.tools.TableUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.Random;

import static eu.coatrack.admin.e2e.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getAdminServicesPage;
import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getDashboard;

public class ServiceOfferings {

    private final WebDriver driver;
    private final TableUtils serviceTableUtils;

    public ServiceOfferings(WebDriver driver) {
        this.driver = driver;
        serviceTableUtils = new TableUtils(driver, TableType.SERVICE_TABLE);
    }

    public String createService() {

        //TODO Replace this block of code by one 'driver.get()' statement.
        driver.get(getDashboard());
        driver.findElement(By.linkText("Service Offering Setup")).click();
        driver.findElement(By.linkText("Service Offerings")).click();
        driver.findElement(By.linkText("Create Service Offering")).click();
        driver.findElement(By.id("name")).click();

        String serviceName = "my-service-" + (new Random().nextInt());
        String serviceId = serviceName + "-id";

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
        return serviceTableUtils.isItemWithinList(serviceName);
    }

    public void deleteService(String serviceName){
        serviceTableUtils.deleteItem(serviceName);
    }

    public void deleteAllServices() {
        serviceTableUtils.deleteAllItem();
    }
}
