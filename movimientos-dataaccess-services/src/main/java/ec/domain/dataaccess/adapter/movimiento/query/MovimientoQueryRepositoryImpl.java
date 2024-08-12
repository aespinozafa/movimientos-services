package ec.domain.dataaccess.adapter.movimiento.query;

import ec.domain.application.ports.output.remote.ClienteRemoteService;
import ec.domain.application.ports.output.repository.persona.query.MovimientoQueryRepository;
import ec.domain.core.exception.MovimientoException;
import ec.domain.core.record.remote.ClienteTo;
import ec.domain.core.record.response.MovimientoReporteResponseRecord;
import ec.domain.core.record.response.MovimientoResponseRecord;
import ec.domain.dataaccess.entities.MovimientoEntity;
import ec.domain.dataaccess.mappers.MovimientoMapper;
import ec.domain.dataaccess.repository.MovimientoJpaRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovimientoQueryRepositoryImpl implements MovimientoQueryRepository {

  private final MovimientoJpaRepository jpaRepository;
  private final ClienteRemoteService clienteRemoteService;

  @Override
  public List<MovimientoResponseRecord> findAllActive() {
    return jpaRepository.findAllActive().stream()
        .map(MovimientoMapper.INSTANCE::entityToResponseRecord)
        .toList();
  }

  @Override
  public List<MovimientoReporteResponseRecord> getReporte(String fechaRango, Integer clienteId) {
    String[] fechas = fechaRango.split("_");
    LocalDate fechaInicio = LocalDate.parse(fechas[0]);
    LocalDate fechaFin = LocalDate.parse(fechas[1]);

    List<MovimientoEntity> movimientos =
        jpaRepository.findMovimientosByClienteAndFecha(clienteId, fechaInicio, fechaFin);

    return movimientos.stream()
        .map(
            movimiento -> {
              // Obtener el cliente remotamente
              CompletableFuture<ClienteTo> clienteFuture =
                  fetchClienteFuture(movimiento.getCuenta().getClienteId());
              ClienteTo cliente = clienteFuture.join();

              String nombreCliente =
                  (cliente != null) ? cliente.personaId().nombre() : "No se encontró el cliente";
              BigDecimal saldoInicial = (movimiento.getSaldo().subtract(movimiento.getValor()));
              return new MovimientoReporteResponseRecord(
                  movimiento.getFecha(),
                  nombreCliente,
                  movimiento.getCuenta().getNumero(),
                  movimiento.getTipo(),
                  saldoInicial,
                  movimiento.getEstado(),
                  movimiento.getValor(),
                  movimiento.getSaldo());
            })
        .toList();
  }

  @Override
  public MovimientoResponseRecord findById(Integer id) {
    MovimientoEntity personaEntity = getEntityById(id);
    return MovimientoMapper.INSTANCE.entityToResponseRecord(personaEntity);
  }

  private CompletableFuture<ClienteTo> fetchClienteFuture(Integer idCliente) {
    return CompletableFuture.supplyAsync(() -> clienteRemoteService.obtenerClientePorId(idCliente));
  }

  protected MovimientoEntity getEntityById(Integer id) {
    return jpaRepository
        .findById(id)
        .orElseThrow(
            () ->
                new MovimientoException(String.format("El movimiento con el id %d no existe", id)));
  }
}
