package eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup;

import eu.coatrack.admin.e2e.api.TableType;
import eu.coatrack.admin.e2e.api.tools.TableUtils;
import org.openqa.selenium.WebDriver;

public class ApiKeys {

    private WebDriver driver;
    private TableUtils apiKeyTableUtils;

    public ApiKeys(WebDriver driver) {
        this.driver = driver;
        apiKeyTableUtils = new TableUtils(driver, TableType.APIKEY_TABLE);
    }

    public void deleteAllApiKeys() {
        apiKeyTableUtils.deleteAllItem();
    }
}
