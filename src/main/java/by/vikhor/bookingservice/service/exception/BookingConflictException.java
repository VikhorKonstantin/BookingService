package by.vikhor.bookingservice.service.exception;

public class BookingConflictException extends Exception {
    public BookingConflictException() {
    }

    public BookingConflictException(String message) {
        super(message);
    }

    public BookingConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
