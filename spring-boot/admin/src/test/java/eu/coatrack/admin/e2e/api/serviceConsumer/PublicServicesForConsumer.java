package eu.coatrack.admin.e2e.api.serviceConsumer;

import eu.coatrack.admin.e2e.api.tools.TableUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static eu.coatrack.admin.e2e.api.tools.TableType.*;
import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getConsumerApiKeyListUrl;

public class PublicServicesForConsumer {

    private final WebDriver driver;
    private final TableUtils consumerServiceTableUtils;
    private final TableUtils consumerApiKeyTableUtils;

    public PublicServicesForConsumer(WebDriver driver) {
        this.driver = driver;
        consumerServiceTableUtils = new TableUtils(driver, CONSUMER_SERVICE_TABLE);
        consumerApiKeyTableUtils = new TableUtils(driver, CONSUMER_APIKEY_TABLE);
    }

    public String createApiKeyFromPublicService(String serviceName) {
        List<WebElement> rowsBeforeApiKeyCreation = consumerApiKeyTableUtils.getItemRows();
        consumerServiceTableUtils.createApiKeyFromPublicService(serviceName);
        List<WebElement> rowsAfterApiKeyCreation = consumerApiKeyTableUtils.getItemRows();

        rowsAfterApiKeyCreation.removeAll(rowsBeforeApiKeyCreation);
        String apiKeyValue = rowsAfterApiKeyCreation.get(0).findElements(By.cssSelector("td")).get(3).getText();
        return apiKeyValue;
    }
}
