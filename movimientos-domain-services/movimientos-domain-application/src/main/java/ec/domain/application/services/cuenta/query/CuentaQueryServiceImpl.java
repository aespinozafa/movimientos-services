package ec.domain.application.services.cuenta.query;

import ec.domain.application.ports.input.cuenta.query.CuentaQueryService;
import ec.domain.application.ports.output.repository.cliente.query.CuentaQueryRepository;
import ec.domain.core.record.response.CuentaResponseRecord;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CuentaQueryServiceImpl implements CuentaQueryService {

  private final CuentaQueryRepository queryRepository;

  @Override
  public List<CuentaResponseRecord> findAllActive() {

    return queryRepository.findAllActive();
  }

  @Override
  public CuentaResponseRecord findById(Integer id) {
    return queryRepository.findById(id);
  }
}
