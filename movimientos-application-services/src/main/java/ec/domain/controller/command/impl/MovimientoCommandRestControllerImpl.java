package ec.domain.controller.command.impl;

import ec.domain.application.ports.input.movimiento.command.MovimientoCommandService;
import ec.domain.controller.command.MovimientoCommandRestController;
import ec.domain.core.record.request.MovimientoRequestRecord;
import ec.domain.core.record.response.MovimientoResponseRecord;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovimientoCommandRestControllerImpl implements MovimientoCommandRestController {

  private final MovimientoCommandService commandService;

  @Override
  public MovimientoResponseRecord createOrUpdatePersona(
      @Valid MovimientoRequestRecord requestRecord) {
    return commandService.createOrUpdate(requestRecord);
  }

  @Override
  public ResponseEntity<String> delete(Integer id) {
    commandService.delete(id);
    return ResponseEntity.ok("Registro borrado exitosamente");
  }
}
