package ec.domain.application.services.cuenta.command;

import ec.domain.application.ports.input.cuenta.command.CuentaCommandService;
import ec.domain.application.ports.output.repository.cliente.command.CuentaCommandRepository;
import ec.domain.core.record.request.CuentaRequestRecord;
import ec.domain.core.record.response.CuentaResponseRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CuentaCommandServiceImpl implements CuentaCommandService {

  private final CuentaCommandRepository commandRepository;

  @Override
  @Transactional
  public CuentaResponseRecord createOrUpdate(CuentaRequestRecord requestRecord) {
    return commandRepository.createOrUpdate(requestRecord);
  }

  @Override
  @Transactional
  public void delete(Integer id) {
    commandRepository.delete(id);
  }
}
