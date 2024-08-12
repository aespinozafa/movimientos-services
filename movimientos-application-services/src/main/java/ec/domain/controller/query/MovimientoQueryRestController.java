package ec.domain.controller.query;

import ec.domain.core.record.response.MovimientoReporteResponseRecord;
import ec.domain.core.record.response.MovimientoResponseRecord;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/query/movimientos")
@Validated
public interface MovimientoQueryRestController {

  @GetMapping("/all")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Obtiene los movimientos activos.")
  List<MovimientoResponseRecord> findAllActive();

  @GetMapping("/{id}")
  @Operation(summary = "Obtiene registro por el id")
  ResponseEntity<MovimientoResponseRecord> findById(@PathVariable("id") Integer id);

  @GetMapping("/reportes")
  List<MovimientoReporteResponseRecord> getReporte(
      @RequestParam("fecha") String fechaRango, @RequestParam("cliente_id") Integer clienteId);
}
