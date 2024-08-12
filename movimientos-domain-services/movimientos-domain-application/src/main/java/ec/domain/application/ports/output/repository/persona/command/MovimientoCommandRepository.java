package ec.domain.application.ports.output.repository.persona.command;

import ec.domain.core.record.request.MovimientoRequestRecord;
import ec.domain.core.record.response.MovimientoResponseRecord;

public interface MovimientoCommandRepository {

  MovimientoResponseRecord createOrUpdate(MovimientoRequestRecord requestRecord);

  void delete(Integer id);
}
