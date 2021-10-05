package eu.coatrack.admin.selenium.exceptions;

public class GatewayDownloadFailedException extends RuntimeException {
    public GatewayDownloadFailedException(String errorMessage) {
        super(errorMessage);
    }
}
