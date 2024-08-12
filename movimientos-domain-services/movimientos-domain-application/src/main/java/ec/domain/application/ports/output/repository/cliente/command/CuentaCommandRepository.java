package ec.domain.application.ports.output.repository.cliente.command;

import ec.domain.core.record.request.CuentaRequestRecord;
import ec.domain.core.record.response.CuentaResponseRecord;

public interface CuentaCommandRepository {

  CuentaResponseRecord createOrUpdate(CuentaRequestRecord requestRecord);

  void delete(Integer id);
}
