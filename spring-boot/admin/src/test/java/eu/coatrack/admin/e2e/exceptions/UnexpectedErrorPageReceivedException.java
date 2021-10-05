package eu.coatrack.admin.e2e.exceptions;

public class UnexpectedErrorPageReceivedException extends RuntimeException {
    public UnexpectedErrorPageReceivedException(String errorMessage) {
        super(errorMessage);
    }
}
