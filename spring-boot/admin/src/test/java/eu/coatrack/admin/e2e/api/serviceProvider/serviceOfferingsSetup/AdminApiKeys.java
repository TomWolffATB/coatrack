package eu.coatrack.admin.e2e.api.serviceProvider.serviceOfferingsSetup;

import eu.coatrack.admin.e2e.api.tools.TableType;
import eu.coatrack.admin.e2e.api.tools.TableUtils;
import eu.coatrack.admin.e2e.api.tools.WaiterUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static eu.coatrack.admin.e2e.api.tools.WaiterUtils.sleepMillis;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.getAdminApiKeyListUrl;
import static eu.coatrack.admin.e2e.configuration.PageConfiguration.getUsername;
import static eu.coatrack.admin.e2e.configuration.TableConfiguration.*;

public class AdminApiKeys {

    private final WebDriver driver;
    private final TableUtils apiKeyTableUtils;
    private final WaiterUtils waiterUtils;

    public AdminApiKeys(WebDriver driver) {
        this.driver = driver;
        apiKeyTableUtils = new TableUtils(driver, TableType.APIKEY_TABLE);
        waiterUtils = new WaiterUtils(driver);
    }

    public void deleteAllApiKeys() {
        apiKeyTableUtils.deleteAllItem();
    }

    public String createApiKey(String serviceName) {
        driver.get(getAdminApiKeyListUrl());

        List<String> listOfApiKeyValuesBeforeCreation = apiKeyTableUtils.getListOfColumnValues(2);
        workThroughApiKeyCreationMenuForService(serviceName);
        List<String> listOfApiKeyValuesAfterCreation = apiKeyTableUtils.getListOfColumnValues(2);;

        listOfApiKeyValuesAfterCreation.removeAll(listOfApiKeyValuesBeforeCreation);
        String valueOfNewApiKey = listOfApiKeyValuesAfterCreation.get(0);
        return valueOfNewApiKey;
    }

    private void workThroughApiKeyCreationMenuForService(String serviceName) {
        driver.findElement(By.linkText("Create API Key")).click();

        WebElement dropdown = driver.findElement(By.id("selectedServiceId"));
        dropdown.findElements(By.cssSelector("option")).stream().filter(option -> option.getText().contains(serviceName)).findFirst().get().click();

        driver.findElement(By.id("githubUserSearchCriteria")).click();
        driver.findElement(By.id("githubUserSearchCriteria")).sendKeys(getUsername());
        driver.findElement(By.id("githubUserSearchButton")).click();

        sleepMillis(1000);
        driver.findElement(By.cssSelector("td:nth-child(2)")).click();
        driver.findElement(By.id("saveApiKeyButton")).click();
    }

    public boolean isApiKeyWithinList(String apiKeyValue) {
        return apiKeyTableUtils.isItemWithinList(apiKeyValue);
    }

    public void deleteApiKey(String apiKeyValue) {
        apiKeyTableUtils.deleteItem(apiKeyValue);
    }

    public void clickOnCalenderButtonOfApiKey(String apiKeyValue) {
        apiKeyTableUtils.clickOnButton(apiKeyValue, adminApiKeysCalenderButtonColumn, adminApiKeysCalenderButtonClassName);
        sleepMillis(2000);
        driver.get(getAdminApiKeyListUrl());
    }

    public void clickOnDetailsButtonOfApiKey(String apiKeyValue) {
        apiKeyTableUtils.clickOnButton(apiKeyValue, adminApiKeysDetailsButtonColumn, adminApiKeysDetailsButtonClassName);
    }

    public void clickOnEditButtonOfApiKey(String apiKeyValue) {
        apiKeyTableUtils.clickOnButton(apiKeyValue, adminApiKeysEditButtonColumn, adminApiKeysEditButtonClassName);
    }
}
