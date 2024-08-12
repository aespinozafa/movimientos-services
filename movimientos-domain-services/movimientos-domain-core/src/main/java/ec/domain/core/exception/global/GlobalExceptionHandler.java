package ec.domain.core.exception.global;

import jakarta.validation.ConstraintViolationException;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    FieldError fieldError = ex.getBindingResult().getFieldError();
    if (fieldError != null) {
      return new ResponseEntity<>(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolationException(
      ConstraintViolationException ex) {
    String errorMessage =
        ex.getConstraintViolations().stream()
            .map(violation -> violation.getMessage())
            .collect(Collectors.joining(", "));

    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }
}
