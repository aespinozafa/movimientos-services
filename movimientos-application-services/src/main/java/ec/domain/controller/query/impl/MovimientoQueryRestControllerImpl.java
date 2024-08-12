package ec.domain.controller.query.impl;

import ec.domain.application.ports.input.movimiento.query.MovimientoQueryService;
import ec.domain.controller.query.MovimientoQueryRestController;
import ec.domain.core.record.response.MovimientoReporteResponseRecord;
import ec.domain.core.record.response.MovimientoResponseRecord;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovimientoQueryRestControllerImpl implements MovimientoQueryRestController {

  private final MovimientoQueryService queryService;

  @Override
  public List<MovimientoResponseRecord> findAllActive() {
    return queryService.findAllActive();
  }

  @Override
  public ResponseEntity<MovimientoResponseRecord> findById(Integer id) {
    return ResponseEntity.ok(queryService.findById(id));
  }

  @Override
  public List<MovimientoReporteResponseRecord> getReporte(String fechaRango, Integer clienteId) {
    return queryService.getReporte(fechaRango, clienteId);
  }
}
