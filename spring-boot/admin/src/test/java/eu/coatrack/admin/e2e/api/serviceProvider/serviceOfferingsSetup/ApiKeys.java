package eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup;

import eu.coatrack.admin.e2e.api.TableType;
import eu.coatrack.admin.e2e.api.tools.TableUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static eu.coatrack.admin.e2e.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.e2e.configuration.TestConfiguration.getAdminApiKeysPage;

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

    public String createApiKey() {
        driver.get(getAdminApiKeysPage());

        List<String> listOfApiKeyValuesBeforeCreation = apiKeyTableUtils.getListOfColumnValues(2);

        driver.findElement(By.linkText("Create API Key")).click();
        driver.findElement(By.id("selectedServiceId")).click();
        driver.findElement(By.cssSelector("#selectedServiceId > option")).click();
        driver.findElement(By.id("githubUserSearchCriteria")).click();
        driver.findElement(By.id("githubUserSearchCriteria")).sendKeys("ChristophBaierATB");
        driver.findElement(By.id("githubUserSearchButton")).click();

        //TODO Sleep methods should be replaced by explicit waits.
        sleepMillis(1000);

        driver.findElement(By.cssSelector("td:nth-child(2)")).click();
        driver.findElement(By.id("saveApiKeyButton")).click();

        List<String> listOfApiKeyValuesAfterCreation = apiKeyTableUtils.getListOfColumnValues(2);;
        listOfApiKeyValuesAfterCreation.removeAll(listOfApiKeyValuesBeforeCreation);

        String valueOfNewApiKey = listOfApiKeyValuesAfterCreation.get(0);
        return valueOfNewApiKey;
    }

    public boolean isApiKeyWithinList(String apiKeyValue) {
        return apiKeyTableUtils.isItemWithinList(apiKeyValue);
    }

    public void deleteApiKey(String apiKeyValue) {
        apiKeyTableUtils.deleteItem(apiKeyValue);
    }
}
