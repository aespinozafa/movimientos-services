package ec.domain.application.ports.input.cuenta.query;

import ec.domain.core.record.response.CuentaResponseRecord;
import java.util.List;

public interface CuentaQueryService {

  List<CuentaResponseRecord> findAllActive();

  CuentaResponseRecord findById(Integer id);
}
