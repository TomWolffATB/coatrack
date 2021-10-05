package eu.coatrack.admin.selenium.api.pages.serviceProvider;

import eu.coatrack.admin.selenium.api.pages.AbstractDashboardTemplate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static eu.coatrack.admin.selenium.api.PageFactory.urlReachabilityTools;
import static eu.coatrack.admin.selenium.api.UtilFactory.driver;
import static eu.coatrack.admin.selenium.configuration.PageConfiguration.serviceProviderDashboardUrl;
import static eu.coatrack.admin.selenium.configuration.PageConfiguration.username;

public class ServiceProviderDashboard extends AbstractDashboardTemplate {

    public ServiceProviderDashboard() {
        super(serviceProviderDashboardUrl);
    }

    public int getApiUsageTrend() {
        return getIntegerValueOfElementWithId("callsDiff");
    }

    public int getNumberOfUsers() {
        return getIntegerValueOfElementWithId("users");
    }

    public int getCallsOfLoggedInUser() {
        urlReachabilityTools.fastVisit(serviceProviderDashboardUrl);
        List<String> list = driver.findElement(By.id("userStatisticsTable")).findElements(By.cssSelector("span"))
                .stream().map(WebElement::getText).collect(Collectors.toList());

        for (int i = 0; i < list.size(); i += 2){
            if (list.get(i).equals(username))
                return Integer.parseInt(list.get(i+1));
        }
        return 0;
    }

}
