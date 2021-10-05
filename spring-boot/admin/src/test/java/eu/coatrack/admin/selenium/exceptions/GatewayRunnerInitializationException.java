package eu.coatrack.admin.selenium.exceptions;

public class GatewayRunnerInitializationException extends RuntimeException {
    public GatewayRunnerInitializationException(String s, Exception e) {
        super(s, e);
    }
}
