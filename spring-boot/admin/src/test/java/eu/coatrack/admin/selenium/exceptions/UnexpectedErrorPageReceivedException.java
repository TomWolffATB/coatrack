package eu.coatrack.admin.selenium.exceptions;

public class UnexpectedErrorPageReceivedException extends RuntimeException {
    public UnexpectedErrorPageReceivedException(String errorMessage) {
        super(errorMessage);
    }
}
