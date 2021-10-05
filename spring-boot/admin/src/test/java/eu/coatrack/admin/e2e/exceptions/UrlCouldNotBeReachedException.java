package eu.coatrack.admin.e2e.exceptions;

public class UrlCouldNotBeReachedException extends RuntimeException {
    public UrlCouldNotBeReachedException(String textMessage) {
        super(textMessage);
    }
}
