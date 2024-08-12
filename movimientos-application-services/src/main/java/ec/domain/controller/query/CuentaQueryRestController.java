package ec.domain.controller.query;

import ec.domain.core.record.response.CuentaResponseRecord;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/query/cuentas")
@Validated
public interface CuentaQueryRestController {

  @GetMapping("/all")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Obtiene las cuentas activos.")
  List<CuentaResponseRecord> findAllActive();

  @GetMapping("/{id}")
  @Operation(summary = "Obtiene registro por el id")
  ResponseEntity<CuentaResponseRecord> findById(@PathVariable("id") Integer id);
}
