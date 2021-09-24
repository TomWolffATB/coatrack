package eu.coatrack.admin.e2e.api.serviceConsumer;

import eu.coatrack.admin.e2e.api.tools.TableType;
import eu.coatrack.admin.e2e.api.tools.TableUtils;
import org.openqa.selenium.WebDriver;

public class ConsumerApiKeyList {

    private final TableUtils consumerApiKeyTableUtils;

    public ConsumerApiKeyList(WebDriver driver) {
        consumerApiKeyTableUtils = new TableUtils(driver, TableType.CONSUMER_APIKEY_TABLE);
    }

    public boolean isApiKeyWithinList(String apiKeyValue){
        return consumerApiKeyTableUtils.isItemWithinList(apiKeyValue);
    }

    public void deletePublicApiKey(String apiKeyValue) {
        consumerApiKeyTableUtils.deleteItem(apiKeyValue);
    }
}
