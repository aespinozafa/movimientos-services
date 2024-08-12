package ec.domain.dataaccess.adapter.cuenta.query;

import ec.domain.application.ports.output.remote.ClienteRemoteService;
import ec.domain.application.ports.output.repository.cliente.query.CuentaQueryRepository;
import ec.domain.core.exception.CuentaException;
import ec.domain.core.record.remote.ClienteTo;
import ec.domain.core.record.response.CuentaResponseRecord;
import ec.domain.dataaccess.entities.CuentaEntity;
import ec.domain.dataaccess.repository.CuentaJpaRepository;
import ec.domain.logger.LoggerUtil;
import feign.FeignException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CuentaQueryRepositoryImpl implements CuentaQueryRepository {

  private final CuentaJpaRepository jpaRepository;
  private final ClienteRemoteService clienteRemoteService;

  @Override
  public List<CuentaResponseRecord> findAllActive() {

    return jpaRepository.findAllActive().stream()
        .map(
            entity -> {
              String nombre = "";
              try {
                CompletableFuture<ClienteTo> clienteFuture =
                    fetchClienteFuture(entity.getClienteId());
                ClienteTo cliente = clienteFuture.join();
                nombre = cliente.personaId().nombre();
              } catch (Exception e) {
                nombre = "No se encontro el cliente";
              }
              return new CuentaResponseRecord(
                  entity.getId(),
                  entity.getNumero(),
                  entity.getTipo(),
                  entity.getSaldoInicial(),
                  entity.getEstado(),
                  nombre);
            })
        .toList();
  }

  @Override
  public CuentaResponseRecord findById(Integer id) {
    CuentaEntity entity = getClienteEntityById(id);
    return fetchClienteFuture(entity.getClienteId())
        .thenApply(
            cliente -> {
              String nombre = "";
              if (cliente != null) {
                nombre = cliente.personaId().nombre();
              }
              return new CuentaResponseRecord(
                  entity.getId(),
                  entity.getNumero(),
                  entity.getTipo(),
                  entity.getSaldoInicial(),
                  entity.getEstado(),
                  nombre);
            })
        .join();
  }

  public CuentaEntity getClienteEntityById(Integer id) {
    return jpaRepository
        .findById(id)
        .orElseThrow(
            () -> new CuentaException(String.format("El registro con el id %d no existe", id)));
  }

  private CompletableFuture<ClienteTo> fetchClienteFuture(Integer idCliente) {
    return CompletableFuture.supplyAsync(
        () -> {
          long start = System.currentTimeMillis();
          try {
            ClienteTo result = clienteRemoteService.obtenerClientePorId(idCliente);
            LoggerUtil.info("Cliente duration: " + (System.currentTimeMillis() - start) + "ms");
            return result;
          } catch (FeignException.NotFound e) {
            LoggerUtil.error("Cliente not found for ID: " + idCliente, e);
            return null;
          } catch (Exception e) {
            LoggerUtil.error("Error fetching Cliente for ID: " + idCliente, e);
            return null;
          }
        });
  }
}
