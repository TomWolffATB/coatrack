package eu.coatrack.system_integration_testing.exceptions;

public class RemoteWebDriverCreationFailedException extends RuntimeException {
    public RemoteWebDriverCreationFailedException(String message, Exception e) {
        super(message, e);
    }
}
