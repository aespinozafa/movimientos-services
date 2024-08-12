package ec.domain.application.ports.input.movimiento.command;

import ec.domain.core.record.request.MovimientoRequestRecord;
import ec.domain.core.record.response.MovimientoResponseRecord;

public interface MovimientoCommandService {

  MovimientoResponseRecord createOrUpdate(MovimientoRequestRecord requestRecord);

  void delete(Integer id);
}
