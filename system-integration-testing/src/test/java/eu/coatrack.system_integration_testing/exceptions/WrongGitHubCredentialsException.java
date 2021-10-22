package eu.coatrack.system_integration_testing.exceptions;

public class WrongGitHubCredentialsException extends RuntimeException {
    public WrongGitHubCredentialsException(String errorMessage) {
        super(errorMessage);
    }
}
