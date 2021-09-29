package eu.coatrack.admin.e2e.exceptions;

public class GatewayRunnerInitializationException extends RuntimeException {
    public GatewayRunnerInitializationException(String s, Exception e) {
        super(s, e);
    }
}
