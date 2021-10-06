package eu.coatrack.admin.selenium.configuration;

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

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class PageConfiguration {

    public static final String username;
    public static final String password;
    public static final String host;
    public static final String startpageUrl;

    static {
        File configFile = new File("config.properties");

        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);

            username = props.getProperty("username");
            password = props.getProperty("password");
            String protocol = props.getProperty("protocol");
            host = props.getProperty("host");
            String port = props.getProperty("port");

            if (host.equals("localhost"))
                startpageUrl = protocol + "://" + host + ":" + port;
            else
                startpageUrl = protocol + "://" + host;

            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("The reading of the page configuration failed.", e);
        }
    }

    public static final String serviceProviderDashboardUrl      = startpageUrl + "/admin";
    public static final String serviceProviderTutorialUrl       = startpageUrl + "/admin/gettingstarted";
    public static final String serviceProviderServicesUrl       = startpageUrl + "/admin/services";
    public static final String serviceProviderGatewaysUrl       = startpageUrl + "/admin/proxies";
    public static final String serviceProviderApiKeysUrl        = startpageUrl + "/admin/api-keys";
    public static final String serviceProviderReportsUrl        = startpageUrl + "/admin/reports";

    public static final String serviceConsumerDashboardUrl      = startpageUrl + "/admin/consumer";
    public static final String serviceConsumerTutorialUrl       = startpageUrl + "/admin/consumer/gettingstarted";
    public static final String serviceConsumerApiKeyListUrl     = startpageUrl + "/admin/api-keys/consumer/list";
    public static final String serviceConsumerServiceListUrl    = startpageUrl + "/admin/services/consumer/list";
    public static final String serviceConsumerReportsUrl        = startpageUrl + "/admin/reports/consumer";

    public static final String exampleServiceUrl                = "http://example.org/";
    public static final String localGatewayAccessUrl            = "http://localhost:8088";

}
