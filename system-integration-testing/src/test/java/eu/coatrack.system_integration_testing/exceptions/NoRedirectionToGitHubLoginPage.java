package eu.coatrack.system_integration_testing.exceptions;

public class NoRedirectionToGitHubLoginPage extends RuntimeException {
    public NoRedirectionToGitHubLoginPage(String errorMessage) {
        super(errorMessage);
    }
}
