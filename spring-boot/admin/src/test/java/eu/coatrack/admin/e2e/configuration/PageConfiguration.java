package eu.coatrack.admin.e2e.configuration;

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

    //Input Parameters
    public static final String username;
    public static final String password;

    //Page URLs
    public static final String host;
    public static final String startpageUrl;
    public static final String adminDashboardUrl;
    public static final String adminTutorialUrl;
    public static final String adminServiceListUrl;
    public static final String adminGatewayListUrl;
    public static final String adminApiKeyListUrl;
    public static final String adminReportsUrl;

    public static final String consumerDashboardUrl;
    public static final String consumerTutorialUrl;
    public static final String consumerApiKeyListUrl;
    public static final String consumerServiceListUrl;
    public static final String consumerReportsUrl;

    public static final String providerServiceUrl;
    public static final String gatewayAccessUrl;

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

            adminDashboardUrl       = startpageUrl + props.getProperty("adminDashboardUrl");
            adminTutorialUrl        = startpageUrl + props.getProperty("adminTutorialUrl");
            adminServiceListUrl     = startpageUrl + props.getProperty("adminServiceListUrl");
            adminGatewayListUrl     = startpageUrl + props.getProperty("adminGatewayListUrl");
            adminApiKeyListUrl      = startpageUrl + props.getProperty("adminApiKeyListUrl");
            adminReportsUrl         = startpageUrl + props.getProperty("adminReportsUrl");

            consumerDashboardUrl    = startpageUrl + props.getProperty("consumerDashboardUrl");
            consumerTutorialUrl     = startpageUrl + props.getProperty("consumerTutorialUrl");
            consumerApiKeyListUrl   = startpageUrl + props.getProperty("consumerApiKeyListUrl");
            consumerServiceListUrl  = startpageUrl + props.getProperty("consumerServiceListUrl");
            consumerReportsUrl      = startpageUrl + props.getProperty("consumerReportsUrl");

            providerServiceUrl      = props.getProperty("providerServiceUrl");
            gatewayAccessUrl      = props.getProperty("gatewayAccessUrl");

            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("The reading of the page configuration failed.", e);
        }
    }

}
