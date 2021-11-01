package eu.coatrack.system_integration_testing.exceptions;

public class HostWasNotReachableWithinAMinuteException extends RuntimeException {
    public HostWasNotReachableWithinAMinuteException(String message) {
        super(message);
    }
}
