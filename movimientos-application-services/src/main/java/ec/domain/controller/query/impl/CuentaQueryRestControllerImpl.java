package ec.domain.controller.query.impl;

import ec.domain.application.ports.input.cuenta.query.CuentaQueryService;
import ec.domain.controller.query.CuentaQueryRestController;
import ec.domain.core.record.response.CuentaResponseRecord;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CuentaQueryRestControllerImpl implements CuentaQueryRestController {

  private final CuentaQueryService queryService;

  @Override
  public List<CuentaResponseRecord> findAllActive() {
    return queryService.findAllActive();
  }

  @Override
  public ResponseEntity<CuentaResponseRecord> findById(Integer id) {
    return ResponseEntity.ok(queryService.findById(id));
  }
}
