package eu.coatrack.admin.e2e.exceptions;

public class CookieSaveFileWritingError extends RuntimeException {
    public CookieSaveFileWritingError(String s, Exception e) {
        super(s, e);
    }
}
