package eu.coatrack.proxy.security.exceptions;

public class ApiKeyValidityCouldNotBeDecidedException extends RuntimeException {
    public ApiKeyValidityCouldNotBeDecidedException(String message) {
        super(message);
    }
}
