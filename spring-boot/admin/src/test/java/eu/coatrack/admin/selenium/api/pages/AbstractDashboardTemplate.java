package eu.coatrack.admin.selenium.api.pages;

import eu.coatrack.admin.selenium.api.tools.UrlReachabilityTools;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class AbstractDashboardTemplate {

    protected final WebDriver driver;
    protected final UrlReachabilityTools urlReachabilityTools;
    protected final String targetUrl;

    public AbstractDashboardTemplate(WebDriver driver, String targetUrl) {
        this.driver = driver;
        urlReachabilityTools = new UrlReachabilityTools(driver);
        this.targetUrl = targetUrl;
    }

    public int getTotalApiCalls() {
        return getIntegerValueOfElementWithId("callsThisPeriod");
    }

    public int getErrorCount() {
        return getIntegerValueOfElementWithId("errorsThisPeriod");
    }

    protected int getIntegerValueOfElementWithId(String elementId){
        urlReachabilityTools.fastVisit(targetUrl);
        WebElement callsThisPeriod = driver.findElement(By.id(elementId));
        return Integer.parseInt(callsThisPeriod.getText());
    }
}
