package ec.domain.core.record.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record MovimientoResponseRecord(
    Long id,
    String numeroCuenta,
    String tipoCuenta,
    BigDecimal saldo,
    String movimiento,
    LocalDate fecha)
    implements Serializable {}
