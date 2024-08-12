package ec.domain.controller.command.impl;

import ec.domain.application.ports.input.cuenta.command.CuentaCommandService;
import ec.domain.controller.command.CuentaCommandRestController;
import ec.domain.core.record.request.CuentaRequestRecord;
import ec.domain.core.record.response.CuentaResponseRecord;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CuentaCommandRestControllerImpl implements CuentaCommandRestController {

  private final CuentaCommandService commandService;

  @Override
  public CuentaResponseRecord createOrUpdatePersona(@Valid CuentaRequestRecord requestRecord) {
    return commandService.createOrUpdate(requestRecord);
  }

  @Override
  public ResponseEntity<String> delete(Integer id) {
    commandService.delete(id);
    return ResponseEntity.ok("Registro borrado exitosamente");
  }
}
