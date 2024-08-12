package ec.domain.core.record.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record MovimientoRequestRecord(
    Integer id,
    @NotNull(message = "{movimiento.fecha.notNull}") LocalDate fecha,
    @NotBlank(message = "{movimiento.tipo.notBlank}") String tipo,
    @NotNull(message = "{movimiento.valor.notNull}") BigDecimal valor,
    @NotNull(message = "{movimiento.saldo.notNull}") BigDecimal saldo,
    Boolean estado,
    @NotNull(message = "{movimiento.cuentaId.notNull}") Integer cuentaId,
    String numeroCuenta) {}
