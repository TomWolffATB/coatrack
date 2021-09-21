package eu.coatrack.admin.e2e.configuration;

public class TestConfiguration {

    //Input Parameters
    private static String username = "user";
    private static String password = "password";
    private static String protocol = "http";
    private static String host = "localhost";
    private static String port = "8080";

    //Page URLs
    private static String defaultPage = protocol + "://" + host + ":" + port;
    private static String dashboard = defaultPage + "/admin";
    private static String adminServicesPage = defaultPage + "/admin/services";
    private static String adminTutorialPage = defaultPage + "/admin/gettingstarted";
    private static String adminGatewaysPage = defaultPage + "/admin/proxies";
    private static String adminApiKeysPage = defaultPage + "/admin/api-keys";

    public static String getAdminApiKeysPage() {
        return adminApiKeysPage;
    }

    public static String getAdminGatewaysPage() {
        return adminGatewaysPage;
    }

    public static String getAdminTutorialPage() {
        return adminTutorialPage;
    }

    public static String getAdminServicesPage() {
        return adminServicesPage;
    }

    public static String getDefaultPage() {
        return defaultPage;
    }

    public static String getDashboard() {
        return dashboard;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

}
