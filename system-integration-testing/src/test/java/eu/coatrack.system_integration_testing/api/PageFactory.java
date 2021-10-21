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
import eu.coatrack.system_integration_testing.exceptions.UnsupportedOperatingSystemException;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static eu.coatrack.system_integration_testing.api.tools.WaiterUtils.waitUntilHostListensOnPort;
import static eu.coatrack.system_integration_testing.configuration.CookieInjector.injectAuthenticationCookieToDriver;
import static eu.coatrack.system_integration_testing.configuration.PageConfiguration.startpageUrl;

public class PageFactory {

    static final WebDriver driver = createWebDriver();

    private static WebDriver createWebDriver() {
        WebDriver driver;
        if (SystemUtils.IS_OS_LINUX) {
            driver = createRemoteWebDriver();
        } else if (SystemUtils.IS_OS_WINDOWS) {
            driver = new FirefoxDriver();
        } else {
            throw new UnsupportedOperatingSystemException("Only Windows and Linux are allowed as Operating Systems.");
        }
        return driver;
    }

    private static WebDriver createRemoteWebDriver() {
        WebDriver remoteWebDriver = null;
        URL remoteWebDriverUrl;
        String host = "selenium-server";
        int port = 4444;
        try {
            remoteWebDriverUrl = new URL("http://" + host + ":" + port);
            waitUntilHostListensOnPort(host, port);
            remoteWebDriver = new RemoteWebDriver(remoteWebDriverUrl, new FirefoxOptions());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return remoteWebDriver;
    }

    public static final UrlReachabilityTools urlReachabilityTools        = new UrlReachabilityTools();
    public static final GatewayRunner gatewayRunner               = new GatewayRunner();

    public static final LoginPage loginPage                   = new LoginPage();

    public static final ServiceProviderDashboard serviceProviderDashboard    = new ServiceProviderDashboard();
    public static final ServiceProviderTutorial serviceProviderTutorial     = new ServiceProviderTutorial();
    public static final ServiceProviderServices serviceProviderServices     = new ServiceProviderServices();
    public static final ServiceProviderGateways serviceProviderGateways     = new ServiceProviderGateways();
    public static final ServiceProviderApiKeys serviceProviderApiKeys      = new ServiceProviderApiKeys();
    public static final ServiceProviderReports serviceProviderReports      = new ServiceProviderReports();

    public static final ServiceConsumerServices serviceConsumerServices     = new ServiceConsumerServices();
    public static final ServiceConsumerApiKeys serviceConsumerApiKeys      = new ServiceConsumerApiKeys();
    public static final ServiceConsumerDashboard serviceConsumerDashboard    = new ServiceConsumerDashboard();
    public static final ServiceConsumerReports serviceConsumerReports      = new ServiceConsumerReports();
    public static final ServiceConsumerTutorial serviceConsumerTutorial     = new ServiceConsumerTutorial();

    static {
        injectAuthenticationCookieToDriver(driver);
        serviceProviderServices.deleteAllServices();
        serviceProviderGateways.deleteAllGateways();
        //TODO The if condition can be removed when the API key deletion button is working on dev.coatrack.eu again.
        if (!startpageUrl.contains("dev.coatrack.eu"))
            serviceProviderApiKeys.deleteAllApiKeys();
    }
}
