package eu.coatrack.system_integration_testing.exceptions;

public class UnsupportedTwoFactorAuthenticationException extends RuntimeException {
    public UnsupportedTwoFactorAuthenticationException(String errorMessage) {
        super(errorMessage);
    }
}
