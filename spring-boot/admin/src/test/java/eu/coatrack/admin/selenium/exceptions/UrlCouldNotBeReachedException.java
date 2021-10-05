package eu.coatrack.admin.selenium.exceptions;

public class UrlCouldNotBeReachedException extends RuntimeException {
    public UrlCouldNotBeReachedException(String textMessage) {
        super(textMessage);
    }
}
