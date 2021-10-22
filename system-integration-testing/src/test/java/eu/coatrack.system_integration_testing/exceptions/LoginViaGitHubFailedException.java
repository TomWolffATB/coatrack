package eu.coatrack.system_integration_testing.exceptions;

public class LoginViaGitHubFailedException extends RuntimeException {
    public LoginViaGitHubFailedException(String errorMessage) {
        super(errorMessage);
    }
}
