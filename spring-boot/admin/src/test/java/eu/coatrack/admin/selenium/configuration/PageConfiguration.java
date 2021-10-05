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

    //Input Parameters
    public static final String username;
    public static final String password;

    //Page URLs
    public static final String host;
    public static final String startpageUrl;
    public static final String serviceProviderDashboardUrl;
    public static final String serviceProviderTutorialUrl;
    public static final String serviceProviderServicesUrl;
    public static final String serviceProviderGatewaysUrl;
    public static final String serviceProviderApiKeysUrl;
    public static final String serviceProviderReportsUrl;

    public static final String serviceConsumerDashboardUrl;
    public static final String serviceConsumerTutorialUrl;
    public static final String serviceConsumerApiKeyListUrl;
    public static final String serviceConsumerServiceListUrl;
    public static final String serviceConsumerReportsUrl;

    public static final String exampleServiceUrl;
    public static final String localGatewayAccessUrl;

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

            serviceProviderDashboardUrl = startpageUrl + props.getProperty("adminDashboardUrl");
            serviceProviderTutorialUrl = startpageUrl + props.getProperty("adminTutorialUrl");
            serviceProviderServicesUrl = startpageUrl + props.getProperty("adminServiceListUrl");
            serviceProviderGatewaysUrl = startpageUrl + props.getProperty("adminGatewayListUrl");
            serviceProviderApiKeysUrl = startpageUrl + props.getProperty("adminApiKeyListUrl");
            serviceProviderReportsUrl = startpageUrl + props.getProperty("adminReportsUrl");

            serviceConsumerDashboardUrl = startpageUrl + props.getProperty("consumerDashboardUrl");
            serviceConsumerTutorialUrl = startpageUrl + props.getProperty("consumerTutorialUrl");
            serviceConsumerApiKeyListUrl = startpageUrl + props.getProperty("consumerApiKeyListUrl");
            serviceConsumerServiceListUrl = startpageUrl + props.getProperty("consumerServiceListUrl");
            serviceConsumerReportsUrl = startpageUrl + props.getProperty("consumerReportsUrl");

            exampleServiceUrl = props.getProperty("providerServiceUrl");
            localGatewayAccessUrl = props.getProperty("gatewayAccessUrl");


            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("The reading of the page configuration failed.", e);
        }
    }

}
