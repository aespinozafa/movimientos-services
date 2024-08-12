/**
 * Copyright 2024.
 *
 * <p>Todos los derechos reservados.
 */
package util;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * -- AQUI AÑADIR LA DESCRIPCION DE LA CLASE --.
 *
 * <p>Historial de cambios:
 *
 * @version 1.0.0 $
 */
@Getter
@Setter
public class ErrorDetails {

  private LocalDateTime timestamp;
  private String message;
  private String details;

  public ErrorDetails(LocalDateTime timestamp, String message, String details) {
    super();
    this.timestamp = timestamp;
    this.message = message;
    this.details = details;
  }
}
