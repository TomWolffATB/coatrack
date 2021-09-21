package eu.coatrack.admin.e2e.api.serviceProvider;

public class ItemDto {

    private final String serviceName;
    private final String gatewayDownloadLink;
    private final String apiKeyValue;

    public ItemDto(String serviceName, String gatewayDownloadLink, String apiKeyValue) {
        this.serviceName = serviceName;
        this.gatewayDownloadLink = gatewayDownloadLink;
        this.apiKeyValue = apiKeyValue;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getGatewayDownloadLink() {
        return gatewayDownloadLink;
    }

    public String getApiKeyValue() {
        return apiKeyValue;
    }
}
