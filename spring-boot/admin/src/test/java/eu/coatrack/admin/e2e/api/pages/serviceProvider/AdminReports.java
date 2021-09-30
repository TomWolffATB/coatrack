package eu.coatrack.admin.e2e.api.pages.serviceProvider;

import eu.coatrack.admin.e2e.api.tools.UrlReachabilityTools;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static eu.coatrack.admin.e2e.configuration.PageConfiguration.adminReportsUrl;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.username;

public class AdminReports {

    private final WebDriver driver;

    public AdminReports(WebDriver driver) {
        this.driver = driver;
    }

    public int getNumberOfServiceCalls(String serviceName){
        driver.get(adminReportsUrl);
        driver.findElement(By.id("selectedServiceId")).findElements(By.cssSelector("option")).stream()
                .filter(e -> e.getText().contains(serviceName)).findFirst().get().click();
        driver.findElement(By.id("selectedApiConsumerUserId")).findElements(By.cssSelector("option")).stream()
                .filter(e -> e.getText().contains(username)).findFirst().get().click();
        driver.findElement(By.id("searchBtn")).click();

        List<WebElement> resultRowCells = driver.findElement(By.id("reportTable")).findElement(By.cssSelector("tbody")).findElements(By.cssSelector("td"));
        if (resultRowCells.get(0).getText().contains("No data available in table"))
            return 0;
        else
            return Integer.parseInt(resultRowCells.get(1).getText());
    }
}
