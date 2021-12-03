package eu.coatrack.system_integration_testing.api;

/*-
 * #%L
 * coatrack-admin
 * %%
 * Copyright (C) 2013 - 2021 Corizon | Institut für angewandte Systemtechnik Bremen GmbH (ATB)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import eu.coatrack.system_integration_testing.api.pages.LoginPage;
import eu.coatrack.system_integration_testing.api.pages.serviceConsumer.*;
import eu.coatrack.system_integration_testing.api.pages.serviceProvider.ServiceProviderDashboard;
import eu.coatrack.system_integration_testing.api.pages.serviceProvider.ServiceProviderReports;
import eu.coatrack.system_integration_testing.api.pages.serviceProvider.ServiceProviderTutorial;
import eu.coatrack.system_integration_testing.api.pages.serviceProvider.serviceOfferingsSetup.ServiceProviderApiKeys;
import eu.coatrack.system_integration_testing.api.pages.serviceProvider.serviceOfferingsSetup.ServiceProviderGateways;
import eu.coatrack.system_integration_testing.api.pages.serviceProvider.serviceOfferingsSetup.ServiceProviderServices;
import eu.coatrack.system_integration_testing.api.tools.GatewayRunner;
import eu.coatrack.system_integration_testing.api.tools.UrlReachabilityTools;
import eu.coatrack.system_integration_testing.exceptions.RemoteWebDriverCreationFailedException;
import eu.coatrack.system_integration_testing.exceptions.UnsupportedBrowserDriverException;
import eu.coatrack.system_integration_testing.exceptions.UnsupportedOperatingSystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

import static eu.coatrack.system_integration_testing.api.tools.WaiterUtils.waitUpToTwoMinutesUntilHostListensOnPort;
import static eu.coatrack.system_integration_testing.configuration.CookieInjector.injectAuthenticationCookieToDriver;
import static eu.coatrack.system_integration_testing.configuration.PageConfiguration.*;

@Slf4j
public class PageFactory {

    static final WebDriver driver = createWebDriver();

    private static WebDriver createWebDriver() {
        WebDriver driver;
        if (SystemUtils.IS_OS_LINUX) {
            log.info("Creating RemoteWebDriver via Selenium server.");
            driver = createRemoteWebDriver();
        } else if (SystemUtils.IS_OS_WINDOWS) {
            log.info("Creating WebDriver with local driver instance.");
            driver = new FirefoxDriver();
        } else {
            throw new UnsupportedOperatingSystemException("Only Windows and Linux are allowed as Operating Systems.");
        }
        return driver;
    }

    private static WebDriver createRemoteWebDriver() {
        try {
            URL remoteWebDriverUrl = new URL("http://" + seleniumServerHostName + ":" + seleniumServerPort);
            waitUpToTwoMinutesUntilHostListensOnPort(seleniumServerHostName, seleniumServerPort);
            return new RemoteWebDriver(remoteWebDriverUrl, getBrowserOptions());
        } catch (Exception e) {
            throw new RemoteWebDriverCreationFailedException("The creation of a RemoteWebDriver failed due to:", e);
        }
    }

    private static Capabilities getBrowserOptions() {
        final String BROWSER = System.getenv("BROWSER");
        switch (BROWSER) {
            case "firefox":
                return new FirefoxOptions();
            case "chrome":
                return new ChromeOptions();
            case "edge":
                return new EdgeOptions();
            default:
                throw new UnsupportedBrowserDriverException("The browser " + BROWSER + " is not supported.");
        }
    }

    public static final UrlReachabilityTools urlReachabilityTools           = new UrlReachabilityTools();
    public static final GatewayRunner gatewayRunner                         = new GatewayRunner();

    public static final LoginPage loginPage                                 = new LoginPage();

    public static final ServiceProviderDashboard serviceProviderDashboard   = new ServiceProviderDashboard();
    public static final ServiceProviderTutorial serviceProviderTutorial     = new ServiceProviderTutorial();
    public static final ServiceProviderServices serviceProviderServices     = new ServiceProviderServices();
    public static final ServiceProviderGateways serviceProviderGateways     = new ServiceProviderGateways();
    public static final ServiceProviderApiKeys serviceProviderApiKeys       = new ServiceProviderApiKeys();
    public static final ServiceProviderReports serviceProviderReports       = new ServiceProviderReports();

    public static final ServiceConsumerServices serviceConsumerServices     = new ServiceConsumerServices();
    public static final ServiceConsumerApiKeys serviceConsumerApiKeys       = new ServiceConsumerApiKeys();
    public static final ServiceConsumerDashboard serviceConsumerDashboard   = new ServiceConsumerDashboard();
    public static final ServiceConsumerReports serviceConsumerReports       = new ServiceConsumerReports();
    public static final ServiceConsumerTutorial serviceConsumerTutorial     = new ServiceConsumerTutorial();

    static {
        log.info("Injecting authentication cookie.");
        injectAuthenticationCookieToDriver(driver);

        log.info("Cleanup: Deleting all items of the user.");
        serviceProviderServices.deleteAllServices();
        serviceProviderGateways.deleteAllGateways();
        serviceProviderApiKeys.deleteAllApiKeys();
    }
}
