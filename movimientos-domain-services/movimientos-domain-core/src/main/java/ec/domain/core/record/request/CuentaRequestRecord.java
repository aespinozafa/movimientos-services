package ec.domain.core.record.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CuentaRequestRecord(
    Integer id,
    @NotBlank(message = "{cuenta.numero.notBlank}") String numero,
    @NotBlank(message = "{cuenta.tipo.notBlank}") String tipo,
    @NotNull(message = "{cuenta.saldoInicial.notNull}") Double saldoInicial,
    @NotNull(message = "{cuenta.estado.notNull}") Boolean estado,
    @NotNull(message = "{cuenta.clienteId.notNull}") Integer clienteId) {}
