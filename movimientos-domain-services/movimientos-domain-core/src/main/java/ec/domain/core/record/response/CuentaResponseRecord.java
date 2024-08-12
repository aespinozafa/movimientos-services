package ec.domain.core.record.response;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record CuentaResponseRecord(
    Integer id, String numero, String tipo, BigDecimal saldoInicial, Boolean estado, String cliente)
    implements Serializable {}
