package eu.coatrack.admin.selenium.api.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static eu.coatrack.admin.selenium.api.PageFactory.urlReachabilityTools;
import static eu.coatrack.admin.selenium.api.UtilFactory.driver;

public abstract class AbstractDashboardTemplate {

    protected final String targetUrl;

    public AbstractDashboardTemplate(String targetUrl) {
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
