package eu.coatrack.system_integration_testing.exceptions;

public class UnsupportedBrowserDriverException extends RuntimeException {
    public UnsupportedBrowserDriverException(String message) {
        super(message);
    }
}
