package eu.coatrack.admin.e2e.api.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaiterUtils {

    public static void waitForElementWithId(String id, WebDriver driver) {
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.id(id)));
    }

    public static void sleepMillis(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception ignored){}
    }

}
