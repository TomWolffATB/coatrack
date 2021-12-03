package eu.coatrack.system_integration_testing.exceptions;

public class HostWasNotReachableWithinTimeSpanException extends RuntimeException {
    public HostWasNotReachableWithinTimeSpanException(String message) {
        super(message);
    }
}
