package ec.domain.core.exception;

import ec.domain.core.exception.global.GlobalException;

public class MovimientoException extends GlobalException {

  public MovimientoException(String message) {
    super(message);
  }

  public MovimientoException(String message, Throwable cause) {
    super(message, cause);
  }
}
