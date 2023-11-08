package med.vespa.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // indicates that this class will be considered when exceptions is thrown
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class) // by default, sprint returns 500, so we can return the correct code
    public ResponseEntity handle404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handle400(MethodArgumentNotValidException exception) {
        var errors = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ValidationErrorDTO::new).toList());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationException(ValidationException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handle400(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity handleAuthenticationException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity handleAccessDeniedException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity handle500(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + exception.getLocalizedMessage());
    }

    private record ValidationErrorDTO(String field, String message) {
        public ValidationErrorDTO(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
