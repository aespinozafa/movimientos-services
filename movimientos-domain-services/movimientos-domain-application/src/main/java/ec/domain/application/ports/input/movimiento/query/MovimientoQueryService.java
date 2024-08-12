package ec.domain.application.ports.input.movimiento.query;

import ec.domain.core.record.response.MovimientoReporteResponseRecord;
import ec.domain.core.record.response.MovimientoResponseRecord;
import java.util.List;

public interface MovimientoQueryService {

  List<MovimientoResponseRecord> findAllActive();

  MovimientoResponseRecord findById(Integer id);

  List<MovimientoReporteResponseRecord> getReporte(String fechaRango, Integer clienteId);
}
