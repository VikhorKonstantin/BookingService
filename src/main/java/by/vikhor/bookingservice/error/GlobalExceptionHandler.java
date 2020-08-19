package by.vikhor.bookingservice.error;

import by.vikhor.bookingservice.service.exception.BookingConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(
            HttpServletRequest request, ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(v -> String.format("%s %s", v.getPropertyPath().toString(), v.getMessage()))
                .collect(Collectors.joining("\n"));
        ErrorResponse errorResponse = createGenericResponse(request, message);
        return new ResponseEntity<>(errorResponse,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            HttpServletRequest request, IllegalArgumentException e) {
        ErrorResponse errorResponse = createGenericResponse(request, e.getMessage());
        return new ResponseEntity<>(errorResponse,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingConflictException.class)
    public ResponseEntity<ErrorResponse> handleBookingConflictException(
            HttpServletRequest request, BookingConflictException e) {
        ErrorResponse errorResponse = createGenericResponse(request, e.getMessage());
        return new ResponseEntity<>(errorResponse,
                HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse createGenericResponse(HttpServletRequest request, String message) {
        return ErrorResponse.builder()
                .message(message)
                .path(request.getServletPath())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
