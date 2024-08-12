package ec.domain.application.services.movimiento.command;

import ec.domain.application.ports.input.movimiento.command.MovimientoCommandService;
import ec.domain.application.ports.output.repository.persona.command.MovimientoCommandRepository;
import ec.domain.core.record.request.MovimientoRequestRecord;
import ec.domain.core.record.response.MovimientoResponseRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovimientoCommandServiceImpl implements MovimientoCommandService {

  private final MovimientoCommandRepository commandRepository;

  @Override
  @Transactional
  public MovimientoResponseRecord createOrUpdate(MovimientoRequestRecord requestRecord) {
    return commandRepository.createOrUpdate(requestRecord);
  }

  @Override
  @Transactional
  public void delete(Integer id) {
    commandRepository.delete(id);
  }
}
