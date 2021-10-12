package eu.coatrack.admin.selenium.api.pages.serviceConsumer;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static eu.coatrack.admin.selenium.api.PageFactory.urlReachabilityTools;
import static eu.coatrack.admin.selenium.api.UtilFactory.*;
import static eu.coatrack.admin.selenium.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.selenium.configuration.PageConfiguration.serviceConsumerTutorialUrl;

public class ServiceConsumerTutorial {

    public String doTutorialAndReturnAccessUrlOfExampleService() {
        doServiceConsumerTutorialUntilExampleServiceAccessUrlIsGenereated();

        String exampleServiceAccessUrl = driver.findElement(By.cssSelector("strong")).findElement(By.cssSelector("a")).getAttribute("href");
        throwExceptionIfApiKeyValueFromTableAndAccessUrlAreNotConsistent(exampleServiceAccessUrl);

        driver.findElement(By.className("buttonNext")).sendKeys(Keys.RETURN);
        driver.findElement(By.className("buttonFinish")).sendKeys(Keys.RETURN);
        return exampleServiceAccessUrl;
    }

    private void doServiceConsumerTutorialUntilExampleServiceAccessUrlIsGenereated() {
        driver.get(serviceConsumerTutorialUrl);
        driver.findElement(By.className("buttonNext")).sendKeys(Keys.RETURN);
        waiterUtils.waitForElementWithId("subscribeButton");
        driver.findElement(By.id("subscribeButton")).click();
        sleepMillis(1000);
        driver.findElement(By.className("buttonNext")).sendKeys(Keys.RETURN);
        sleepMillis(1000);
    }

    private void throwExceptionIfApiKeyValueFromTableAndAccessUrlAreNotConsistent(String exampleServiceAccessUrl) {
        String apiKeyValueFromTable = driver.findElement(By.id("apiKeyTable")).findElements(By.cssSelector("td")).get(2).getText();
        String apiKeyValueFromAccessUrl = exampleServiceAccessUrl.split("=")[1];

        if (!apiKeyValueFromTable.equals(apiKeyValueFromAccessUrl))
            throw new RuntimeException("Inconsistent API key values.");
    }

    public boolean isExampleServiceReachableWithAccessUrl(String exampleServiceAccessUrl) {
        urlReachabilityTools.throwExceptionIfUrlIsNotReachable(exampleServiceAccessUrl);
        driver.get(exampleServiceAccessUrl);

        String exampleSpecificTextSnippet = "HelloWorld"; //TODO To be adapted when the example service is working.
        return driver.getPageSource().contains(exampleSpecificTextSnippet);
    }

    public void deleteApiKeyFromExampleServiceAccessUrl(String exampleServiceAccessUrl) {
        String apiKeyValueFromAccessUrl = exampleServiceAccessUrl.split("=")[1];
        //TODO To be uncommented when #56 is solved an merged.
        //serviceConsumerApiKeyTableUtils.deleteItem(apiKeyValueFromAccessUrl);
    }
}
