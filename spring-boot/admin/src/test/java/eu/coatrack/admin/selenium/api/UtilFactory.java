package eu.coatrack.admin.selenium.api;

import eu.coatrack.admin.selenium.api.tools.WaiterUtils;
import eu.coatrack.admin.selenium.api.tools.table.TableUtils;
import org.openqa.selenium.WebDriver;

import static eu.coatrack.admin.selenium.api.tools.table.TableType.*;

public class UtilFactory {

    public static WebDriver driver = PageFactory.driver;

    public static WaiterUtils waiterUtils = new WaiterUtils();

    public static TableUtils serviceProviderApiKeyTableUtils = new TableUtils(PROVIDER_APIKEY_TABLE);
    public static TableUtils serviceProviderGatewayTableUtils = new TableUtils(PROVIDER_GATEWAY_TABLE);
    public static TableUtils serviceProviderServiceTableUtils = new TableUtils(PROVIDER_SERVICE_TABLE);

    public static TableUtils serviceConsumerApiKeyTableUtils = new TableUtils(CONSUMER_APIKEY_TABLE);
    public static TableUtils serviceConsumerServiceTableUtils = new TableUtils(CONSUMER_SERVICE_TABLE);

}
