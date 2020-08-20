package by.vikhor.bookingservice.service.exception;

public class GenericBadRequestException extends Exception {
    public GenericBadRequestException() {
    }

    public GenericBadRequestException(String message) {
        super(message);
    }

    public GenericBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
