package eu.coatrack.admin.e2e.api.pages.serviceProvider;

import eu.coatrack.admin.e2e.api.pages.AbstractDashboardTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static eu.coatrack.admin.e2e.configuration.PageConfiguration.adminDashboardUrl;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.username;

//TODO All the driver field and constructor logic could be simplified by using an abstract super class.
public class AdminDashboard extends AbstractDashboardTemplate {

    public AdminDashboard(WebDriver driver) {
        super(driver, adminDashboardUrl);
    }

    public int getApiUsageTrend() {
        return getIntegerValueOfElementWithId("callsDiff");
    }

    public int getNumberOfUsers() {
        return getIntegerValueOfElementWithId("users");
    }

    public int getCallsOfLoggedInUser() {
        urlReachabilityTools.fastVisit(adminDashboardUrl);
        List<String> list = driver.findElement(By.id("userStatisticsTable")).findElements(By.cssSelector("span"))
                .stream().map(WebElement::getText).collect(Collectors.toList());

        for (int i = 0; i < list.size(); i += 2){
            if (list.get(i).equals(username))
                return Integer.parseInt(list.get(i+1));
        }
        return 0;
    }

}
