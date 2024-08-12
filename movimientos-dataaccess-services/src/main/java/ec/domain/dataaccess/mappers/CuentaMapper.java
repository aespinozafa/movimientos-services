package ec.domain.dataaccess.mappers;

import ec.domain.core.record.request.CuentaRequestRecord;
import ec.domain.core.record.response.CuentaResponseRecord;
import ec.domain.dataaccess.entities.CuentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CuentaMapper {
  CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

  @Mapping(source = "cliente.personaId.nombre", target = "cliente")
  CuentaResponseRecord entityToResponseRecord(CuentaEntity entity);

  CuentaEntity requestRecordToEntity(CuentaRequestRecord requestRecord);

  @Mapping(target = "cliente", ignore = true) // Ignorar el campo cliente para la actualización
  void updateEntityFromRequest(
      CuentaRequestRecord requestRecord, @MappingTarget CuentaEntity entity);
}
