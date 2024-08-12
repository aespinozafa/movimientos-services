package ec.domain.application.services.movimiento.query;

import ec.domain.application.ports.input.movimiento.query.MovimientoQueryService;
import ec.domain.application.ports.output.repository.persona.query.MovimientoQueryRepository;
import ec.domain.core.record.response.MovimientoReporteResponseRecord;
import ec.domain.core.record.response.MovimientoResponseRecord;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovimientoQueryServiceImpl implements MovimientoQueryService {

  private final MovimientoQueryRepository queryRepository;

  @Override
  public List<MovimientoResponseRecord> findAllActive() {

    return queryRepository.findAllActive();
  }

  @Override
  public MovimientoResponseRecord findById(Integer id) {
    return queryRepository.findById(id);
  }

  @Override
  public List<MovimientoReporteResponseRecord> getReporte(String fechaRango, Integer clienteId) {
    return queryRepository.getReporte(fechaRango, clienteId);
  }
}
