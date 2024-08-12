package ec.domain.application.ports.input.cuenta.command;

import ec.domain.core.record.request.CuentaRequestRecord;
import ec.domain.core.record.response.CuentaResponseRecord;

public interface CuentaCommandService {

  CuentaResponseRecord createOrUpdate(CuentaRequestRecord requestRecord);

  void delete(Integer id);
}
