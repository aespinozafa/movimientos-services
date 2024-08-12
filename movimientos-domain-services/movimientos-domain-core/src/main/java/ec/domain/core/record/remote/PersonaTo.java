package ec.domain.core.record.remote;

import java.io.Serializable;
import lombok.Builder;

@Builder
public record PersonaTo(
    Integer id,
    String nombre,
    String genero,
    Integer edad,
    String identificacion,
    String direccion,
    String telefono,
    Boolean estado)
    implements Serializable {}
