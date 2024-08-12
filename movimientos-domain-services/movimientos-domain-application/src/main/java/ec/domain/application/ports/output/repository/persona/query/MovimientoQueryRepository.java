package ec.domain.application.ports.output.repository.persona.query;

import ec.domain.core.record.response.MovimientoReporteResponseRecord;
import ec.domain.core.record.response.MovimientoResponseRecord;
import java.util.List;

public interface MovimientoQueryRepository {
  List<MovimientoResponseRecord> findAllActive();

  List<MovimientoReporteResponseRecord> getReporte(String fechaRango, Integer clienteId);

  MovimientoResponseRecord findById(Integer id);
}
