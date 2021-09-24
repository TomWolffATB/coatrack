package eu.coatrack.admin.e2e.api.serviceProvider;

public class ItemDto {

    public final String serviceName;
    public final String gatewayDownloadLink;
    public final String apiKeyValue;
    public final String gatewayIdentifier;

    public ItemDto(String serviceName, String gatewayDownloadLink, String gatewayIdentifier, String apiKeyValue) {
        this.serviceName = serviceName;
        this.gatewayDownloadLink = gatewayDownloadLink;
        this.apiKeyValue = apiKeyValue;
        this.gatewayIdentifier = gatewayIdentifier;
    }
}
