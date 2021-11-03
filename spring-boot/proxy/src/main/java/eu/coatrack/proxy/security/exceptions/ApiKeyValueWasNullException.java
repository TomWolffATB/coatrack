package eu.coatrack.proxy.security.exceptions;

public class ApiKeyValueWasNullException extends RuntimeException {
    public ApiKeyValueWasNullException(String message) {
        super(message);
    }
}
