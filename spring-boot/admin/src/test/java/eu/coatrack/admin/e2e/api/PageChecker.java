package eu.coatrack.admin.e2e.api;

import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PageChecker {

    private final WebDriver driver;

    public PageChecker(WebDriver driver) {
        this.driver = driver;
    }

    public void assertThatUrlIsReachable(String url){
        driver.get(url);
        assertThatCurrentPageHasNoError();
        assertEquals(url, driver.getCurrentUrl());
    }

    public void assertThatCurrentPageHasNoError(){
        if (driver.getPageSource().contains("Sorry, an error occurred."))
            fail();
    }
}
