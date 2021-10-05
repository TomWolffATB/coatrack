package eu.coatrack.admin.e2e.exceptions;

public class ServiceCouldNotBeAccessedUsingApiKeyException extends RuntimeException {
    public ServiceCouldNotBeAccessedUsingApiKeyException(String errorMessage) {
        super(errorMessage);
    }
}
