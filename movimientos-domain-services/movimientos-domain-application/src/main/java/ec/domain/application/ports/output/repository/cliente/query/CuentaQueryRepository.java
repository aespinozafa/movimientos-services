package ec.domain.application.ports.output.repository.cliente.query;

import ec.domain.core.record.response.CuentaResponseRecord;
import java.util.List;

public interface CuentaQueryRepository {
  List<CuentaResponseRecord> findAllActive();

  CuentaResponseRecord findById(Integer id);
}
