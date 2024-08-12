package ec.domain.dataaccess.adapter.cuenta.command;

import ec.domain.application.ports.output.remote.ClienteRemoteService;
import ec.domain.application.ports.output.repository.cliente.command.CuentaCommandRepository;
import ec.domain.core.exception.CuentaException;
import ec.domain.core.record.remote.ClienteTo;
import ec.domain.core.record.request.CuentaRequestRecord;
import ec.domain.core.record.response.CuentaResponseRecord;
import ec.domain.dataaccess.entities.CuentaEntity;
import ec.domain.dataaccess.mappers.CuentaMapper;
import ec.domain.dataaccess.repository.CuentaJpaRepository;
import ec.domain.logger.LoggerUtil;
import feign.FeignException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.StampedLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CuentaCommandRepositoryImpl implements CuentaCommandRepository {

  private final CuentaJpaRepository jpaRepository;
  private final ClienteRemoteService clienteRemoteService;

  @Override
  @Transactional
  public CuentaResponseRecord createOrUpdate(CuentaRequestRecord requestRecord) {
    CuentaEntity entity = CuentaMapper.INSTANCE.requestRecordToEntity(requestRecord);
    CuentaEntity existingForm = existingForm(entity, requestRecord);

    return fetchClienteFuture(requestRecord.clienteId())
        .thenApply(
            cliente -> {
              if (cliente == null) {
                throw new CuentaException(
                    "El código del cliente no existe: " + requestRecord.clienteId());
              }
              existingForm.setClienteId(cliente.id());
              CuentaEntity savedEntity = jpaRepository.save(existingForm);
              return new CuentaResponseRecord(
                  savedEntity.getId(),
                  savedEntity.getNumero(),
                  savedEntity.getTipo(),
                  savedEntity.getSaldoInicial(),
                  savedEntity.getEstado(),
                  cliente.personaId().nombre());
            })
        .join();
  }

  @Override
  @Transactional
  public void delete(Integer id) {
    var entity =
        jpaRepository
            .findById(id)
            .orElseThrow(() -> new CuentaException("El código no existe: " + id));

    final StampedLock lock = entity.getLock();
    long stamp = lock.writeLock();
    try {
      entity.setEstado(false);
      jpaRepository.save(entity);
    } finally {
      lock.unlockWrite(stamp);
    }
  }

  private CuentaEntity existingForm(CuentaEntity entity, CuentaRequestRecord cuentaRequestRecord) {
    if (Objects.isNull(entity.getId())) {
      return entity;
    }

    CuentaEntity resp =
        jpaRepository
            .findById(entity.getId())
            .orElseThrow(() -> new CuentaException("El codigo  no existe %d." + entity.getId()));

    final StampedLock lock = resp.getLock();
    long stamp = lock.writeLock();
    try {
      CuentaMapper.INSTANCE.updateEntityFromRequest(cuentaRequestRecord, resp);
    } finally {
      lock.unlockWrite(stamp);
    }
    return resp;
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
