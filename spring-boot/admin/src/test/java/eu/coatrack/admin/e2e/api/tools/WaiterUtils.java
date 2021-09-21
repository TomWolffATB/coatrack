package eu.coatrack.admin.e2e.api.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaiterUtils {

    private final WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(WaiterUtils.class);

    public WaiterUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElementWithId(String id) {
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.id(id)));
    }

    public static void sleepMillis(int millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e){
            logger.error("The sleep process failed.", e);
        }
    }

}
