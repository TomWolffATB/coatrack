package eu.coatrack.admin.e2e.api.serviceConsumer;

import eu.coatrack.admin.e2e.api.tools.TableUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static eu.coatrack.admin.e2e.api.tools.TableType.*;

public class ConsumerServiceOfferings {

    private final TableUtils consumerServiceTableUtils;
    private final TableUtils consumerApiKeyTableUtils;

    public ConsumerServiceOfferings(WebDriver driver) {
        consumerServiceTableUtils = new TableUtils(driver, CONSUMER_SERVICE_TABLE);
        consumerApiKeyTableUtils = new TableUtils(driver, CONSUMER_APIKEY_TABLE);
    }

    public String createApiKeyFromPublicService(String serviceName) {
        List<String> apiKeyListBeforeApiKeyCreation = consumerApiKeyTableUtils.getListOfColumnValues(3);
        clickOnCreationButtonInServiceRow(serviceName);
        List<String> apiKeyListAfterApiKeyCreation = consumerApiKeyTableUtils.getListOfColumnValues(3);

        apiKeyListAfterApiKeyCreation.removeAll(apiKeyListBeforeApiKeyCreation);
        String apiKeyValue = apiKeyListAfterApiKeyCreation.get(0);
        return apiKeyValue;
    }

    private void clickOnCreationButtonInServiceRow(String serviceName) {
        WebElement rowOfService = consumerServiceTableUtils.getItemRows().stream()
                .filter(row -> consumerServiceTableUtils.getCellInColumn(row, 0).getText().contains(serviceName))
                .findFirst().get();
        consumerApiKeyTableUtils.getCellInColumn(rowOfService, 4).findElement(By.cssSelector("button")).click();
    }
}
