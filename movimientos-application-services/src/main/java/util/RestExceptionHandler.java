package util;

import ec.domain.core.exception.global.GlobalException;
import ec.domain.logger.LoggerUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Historial de cambios:
 *
 * @version 1.0.0 $
 */
@ControllerAdvice
public class RestExceptionHandler {

  private static final String MENSAJE = "%s %s";

  @ExceptionHandler(GlobalException.class)
  public ResponseEntity<ErrorDetails> handleCommonException(
      GlobalException ex, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
    String stackTraceInfo = getStackTraceInfo(ex);
    LoggerUtil.warn(String.format(MENSAJE, stackTraceInfo, ex.getMessage()));
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex, WebRequest request) {
    ErrorDetails errorDetails =
        new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
    String stackTraceInfo = getStackTraceInfo(ex);
    LoggerUtil.error(String.format(MENSAJE, stackTraceInfo, ex.getMessage()));
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }
    ErrorDetails errorDetails =
        new ErrorDetails(LocalDateTime.now(), "La validación falló: ", errors.toString());
    String stackTraceInfo = getStackTraceInfo(ex);
    LoggerUtil.warn(ex.getMessage() + " " + stackTraceInfo);
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorDetails> handleConstraintViolationException(
      ConstraintViolationException ex, WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
    for (ConstraintViolation<?> violation : violations) {
      String propertyPath = violation.getPropertyPath().toString();
      String message = violation.getMessage();
      errors.put(propertyPath, message);
    }
    ErrorDetails errorDetails =
        new ErrorDetails(LocalDateTime.now(), "Violación de restricciones: ", errors.toString());
    String stackTraceInfo = getStackTraceInfo(ex);
    LoggerUtil.warn(ex.getMessage() + " " + stackTraceInfo);
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  private String getStackTraceInfo(Throwable throwable) {
    StackTraceElement[] stackTraceElements = throwable.getStackTrace();
    if (stackTraceElements.length > 0) {
      StackTraceElement element = stackTraceElements[0];
      return String.format(
          "at %s.%s(%s:%d)",
          element.getClassName(),
          element.getMethodName(),
          element.getFileName(),
          element.getLineNumber());
    }
    return "No stack trace available";
  }
}
