package by.vikhor.bookingservice.error;

import by.vikhor.bookingservice.service.exception.GenericBadRequestException;
import by.vikhor.bookingservice.service.exception.ResourceNotFoundException;
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

    @ExceptionHandler(GenericBadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBookingConflictException(
            HttpServletRequest request, GenericBadRequestException e) {
        ErrorResponse errorResponse = createGenericResponse(request, e.getMessage());
        return new ResponseEntity<>(errorResponse,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            HttpServletRequest request, ResourceNotFoundException e) {
        ErrorResponse errorResponse = createGenericResponse(request, e.getMessage());
        return new ResponseEntity<>(errorResponse,
                HttpStatus.NOT_FOUND);
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
