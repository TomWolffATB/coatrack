package eu.coatrack.admin.e2e.exceptions;

public class FileCouldNotBeDeletedException extends RuntimeException {
    public FileCouldNotBeDeletedException(String errorMessage) {
        super(errorMessage);
    }
}
