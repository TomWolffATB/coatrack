package eu.coatrack.admin.e2e.configuration;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class PageConfiguration {

    //Input Parameters
    public static final String username;
    public static final String password;

    //Page URLs
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

    static {
        File configFile = new File("config.properties");

        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);

            username = props.getProperty("username");
            password = props.getProperty("password");
            String protocol = props.getProperty("protocol");
            String host = props.getProperty("host");
            String port = props.getProperty("port");

            startpageUrl = protocol + "://" + host + ":" + port;
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

            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("The reading of the page configuration failed.", e);
        }
    }

}
