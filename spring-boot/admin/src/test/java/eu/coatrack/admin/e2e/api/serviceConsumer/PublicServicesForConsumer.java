package eu.coatrack.admin.e2e.api.serviceConsumer;

import eu.coatrack.admin.e2e.api.tools.TableUtils;
import org.openqa.selenium.WebDriver;
import java.util.List;

import static eu.coatrack.admin.e2e.api.tools.TableType.*;

public class PublicServicesForConsumer {

    private final TableUtils consumerServiceTableUtils;
    private final TableUtils consumerApiKeyTableUtils;

    public PublicServicesForConsumer(WebDriver driver) {
        consumerServiceTableUtils = new TableUtils(driver, CONSUMER_SERVICE_TABLE);
        consumerApiKeyTableUtils = new TableUtils(driver, CONSUMER_APIKEY_TABLE);
    }

    public String createApiKeyFromPublicService(String serviceName) {
        List<String> apiKeyListBeforeApiKeyCreation = consumerApiKeyTableUtils.getListOfColumnValues(3);
        consumerServiceTableUtils.createApiKeyFromPublicService(serviceName);
        List<String> apiKeyListAfterApiKeyCreation = consumerApiKeyTableUtils.getListOfColumnValues(3);

        apiKeyListAfterApiKeyCreation.removeAll(apiKeyListBeforeApiKeyCreation);
        String apiKeyValue = apiKeyListAfterApiKeyCreation.get(0);
        return apiKeyValue;
    }
}
