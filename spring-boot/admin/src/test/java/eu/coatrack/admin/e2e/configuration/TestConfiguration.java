package eu.coatrack.admin.e2e.configuration;

//TODO All further configuration like column indices should be retrieved from one central config location.
//TODO The whole configuration should be soft-coded.

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class TestConfiguration {

    //Input Parameters
    private static final String username;
    private static final String password;

    //Page URLs
    private static final String startpageUrl;
    private static final String adminDashboardUrl;
    private static final String adminTutorialUrl;
    private static final String adminServiceListUrl;
    private static final String adminGatewayListUrl;
    private static final String adminApiKeyListUrl;
    private static final String adminReportsUrl;

    private static final String consumerDashboardUrl;
    private static final String consumerTutorialUrl;
    private static final String consumerApiKeyListUrl;
    private static final String consumerServiceListUrl;
    private static final String consumerReportsUrl;

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
            throw new RuntimeException("Config went wrong.", e);
        }
    }

    public static String getConsumerDashboardUrl() {
        return consumerDashboardUrl;
    }

    public static String getConsumerTutorialUrl() {
        return consumerTutorialUrl;
    }

    public static String getConsumerApiKeyListUrl() {
        return consumerApiKeyListUrl;
    }

    public static String getConsumerServiceListUrl() {
        return consumerServiceListUrl;
    }

    public static String getConsumerReportsUrl() {
        return consumerReportsUrl;
    }

    public static String getAdminReportsUrl() {
        return adminReportsUrl;
    }

    public static String getAdminApiKeyListUrl() {
        return adminApiKeyListUrl;
    }

    public static String getAdminGatewayListUrl() {
        return adminGatewayListUrl;
    }

    public static String getAdminTutorialUrl() {
        return adminTutorialUrl;
    }

    public static String getAdminServiceListUrl() {
        return adminServiceListUrl;
    }

    public static String getStartpageUrl() {
        return startpageUrl;
    }

    public static String getAdminDashboardUrl() {
        return adminDashboardUrl;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

}
