package eu.coatrack.admin.e2e.configuration;

//TODO All further configuration like column indices should be retrieved from one central config location.
//TODO The whole configuration should be soft-coded.

public class TestConfiguration {

    //Input Parameters
    private static String username = "user";
    private static String password = "password";
    private static String protocol = "http";
    private static String host = "localhost";
    private static String port = "8080";

    //Page URLs
    private static String startpageUrl          = protocol + "://" + host + ":" + port;

    private static String adminDashboardUrl     = startpageUrl + "/admin";
    private static String adminTutorialUrl      = startpageUrl + "/admin/gettingstarted";
    private static String adminServiceListUrl   = startpageUrl + "/admin/services";
    private static String adminGatewayListUrl   = startpageUrl + "/admin/proxies";
    private static String adminApiKeyListUrl    = startpageUrl + "/admin/api-keys";
    private static String adminReportsUrl       = startpageUrl + "/admin/reports";

    private static String consumerDashboardUrl  = startpageUrl + "/admin/consumer";
    private static String consumerTutorialUrl   = startpageUrl + "/admin/consumer/gettingstarted";
    private static String consumerApiKeyListUrl = startpageUrl + "/admin/api-keys/consumer/list";
    private static String consumerServiceListUrl= startpageUrl + "/admin/services/consumer/list";
    private static String consumerReportsUrl    = startpageUrl + "/admin/reports/consumer";

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
