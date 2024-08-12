package ec.domain.core.record.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record MovimientoReporteResponseRecord(
    LocalDate fecha,
    String cliente,
    String numeroCuenta,
    String tipo,
    BigDecimal saldoInicial,
    Boolean estado,
    BigDecimal movimiento,
    BigDecimal saldoDisponible)
    implements Serializable {}
