package eu.coatrack.admin.e2e.exceptions;

public class GatewayDownloadFailedException extends RuntimeException {
    public GatewayDownloadFailedException(String errorMessage) {
        super(errorMessage);
    }
}
