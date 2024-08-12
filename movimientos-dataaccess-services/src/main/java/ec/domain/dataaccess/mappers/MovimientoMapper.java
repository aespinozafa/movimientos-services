package ec.domain.dataaccess.mappers;

import ec.domain.core.record.request.MovimientoRequestRecord;
import ec.domain.core.record.response.MovimientoResponseRecord;
import ec.domain.dataaccess.entities.MovimientoEntity;
import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovimientoMapper {
  MovimientoMapper INSTANCE = Mappers.getMapper(MovimientoMapper.class);

  @Mapping(source = "cuenta.numero", target = "numeroCuenta")
  @Mapping(source = "cuenta.tipo", target = "tipoCuenta")
  @Mapping(source = "saldo", target = "saldo")
  @Mapping(
      target = "movimiento",
      expression = "java(formatMovimiento(entity.getTipo(), entity.getValor()))")
  MovimientoResponseRecord entityToResponseRecord(MovimientoEntity entity);

  MovimientoEntity requestRecordToEntity(MovimientoRequestRecord requestRecord);

  void updateEntityFromRequest(
      MovimientoRequestRecord requestRecord, @MappingTarget MovimientoEntity entity);

  default String formatMovimiento(String tipo, BigDecimal valor) {
    if ("Retiro".equalsIgnoreCase(tipo)) {
      return "Retiro de " + valor;
    } else if ("Depósito".equalsIgnoreCase(tipo) || "Deposito".equalsIgnoreCase(tipo)) {
      return "Depósito de " + valor;
    } else {
      return tipo + " de " + valor;
    }
  }
}
