package eu.coatrack.admin.e2e.api.serviceProvider;

//TODO Remove getters and make fields public.

public class ItemDto {

    private final String serviceName;
    private final String gatewayDownloadLink;
    private final String apiKeyValue;
    private final String gatewayIdentifier;

    public ItemDto(String serviceName, String gatewayDownloadLink, String gatewayIdentifier, String apiKeyValue) {
        this.serviceName = serviceName;
        this.gatewayDownloadLink = gatewayDownloadLink;
        this.apiKeyValue = apiKeyValue;
        this.gatewayIdentifier = gatewayIdentifier;
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

    public String getGatewayIdentifier() {
        return gatewayIdentifier;
    }
}
