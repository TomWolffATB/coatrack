package eu.coatrack.admin.selenium.exceptions;

public class FileCouldNotBeDeletedException extends RuntimeException {
    public FileCouldNotBeDeletedException(String errorMessage) {
        super(errorMessage);
    }
}
