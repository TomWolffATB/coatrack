package eu.coatrack.admin.selenium.exceptions;

public class ServiceCouldNotBeAccessedUsingApiKeyException extends RuntimeException {
    public ServiceCouldNotBeAccessedUsingApiKeyException(String errorMessage) {
        super(errorMessage);
    }
}
