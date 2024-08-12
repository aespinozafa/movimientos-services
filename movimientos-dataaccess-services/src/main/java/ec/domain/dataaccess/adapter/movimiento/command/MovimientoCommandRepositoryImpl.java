package ec.domain.dataaccess.adapter.movimiento.command;

import ec.domain.application.ports.output.repository.persona.command.MovimientoCommandRepository;
import ec.domain.core.exception.CuentaException;
import ec.domain.core.exception.MovimientoException;
import ec.domain.core.record.request.MovimientoRequestRecord;
import ec.domain.core.record.response.MovimientoResponseRecord;
import ec.domain.dataaccess.entities.CuentaEntity;
import ec.domain.dataaccess.entities.MovimientoEntity;
import ec.domain.dataaccess.mappers.MovimientoMapper;
import ec.domain.dataaccess.repository.CuentaJpaRepository;
import ec.domain.dataaccess.repository.MovimientoJpaRepository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.locks.StampedLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovimientoCommandRepositoryImpl implements MovimientoCommandRepository {

  private final MovimientoJpaRepository movimientoJpaRepository;
  private final CuentaJpaRepository cuentaJpaRepository;

  @Override
  @Transactional
  public MovimientoResponseRecord createOrUpdate(MovimientoRequestRecord requestRecord) {
    CuentaEntity cuenta = getCuentaByRequestRecord(requestRecord);
    BigDecimal saldoDisponible = consultarSaldo(cuenta, requestRecord);
    int saldo = saldoDisponible.compareTo(BigDecimal.ZERO);
    if (saldo < 0) {
      throw new MovimientoException("Saldo no disponible");
    }
    MovimientoEntity movimientoEntity = createOrUpdateData(requestRecord);
    MovimientoEntity savedMovimiento = movimientoJpaRepository.save(movimientoEntity);
    return MovimientoMapper.INSTANCE.entityToResponseRecord(savedMovimiento);
  }

  @Override
  @Transactional
  public void delete(Integer id) {
    MovimientoEntity entity =
        movimientoJpaRepository
            .findById(id)
            .orElseThrow(
                () -> new MovimientoException("El codigo de la persona no existe %d." + id));

    entity.setEstado(false);
    movimientoJpaRepository.save(entity);
  }

  private CuentaEntity getCuentaByRequestRecord(MovimientoRequestRecord requestRecord) {
    Optional<CuentaEntity> cuentaEntityOptional;

    if (requestRecord.cuentaId() != null) {
      cuentaEntityOptional = cuentaJpaRepository.findById(requestRecord.cuentaId());
    } else if (requestRecord.numeroCuenta() != null) {
      cuentaEntityOptional = cuentaJpaRepository.findByNumero(requestRecord.numeroCuenta());
    } else {
      throw new MovimientoException("Debe proporcionar un cuentaId o un numero de cuenta.");
    }

    return cuentaEntityOptional.orElseThrow(() -> new MovimientoException("La cuenta no existe."));
  }

  private BigDecimal calcularNuevoSaldo(
      BigDecimal saldoActual, MovimientoRequestRecord requestRecord) {
    BigDecimal valorMovimiento = requestRecord.valor();

    return saldoActual.add(valorMovimiento);
  }

  private MovimientoEntity createOrUpdateData(MovimientoRequestRecord requestRecord) {
    MovimientoEntity entity;

    if (requestRecord.id() != null) {
      entity =
          movimientoJpaRepository
              .findById(requestRecord.id())
              .orElseThrow(
                  () -> new CuentaException("El código no existe %d." + requestRecord.id()));
      // Verificar si hay un movimiento más reciente
      Optional<MovimientoEntity> lastMovimientoOpt =
          movimientoJpaRepository.findLastMovimientoByCuentaId(
              entity.getCuenta().getId(), requestRecord.id());

      if (lastMovimientoOpt.isPresent() && lastMovimientoOpt.get().getId() > requestRecord.id()) {
        throw new MovimientoException(
            "No se puede actualizar el movimiento porque existe un movimiento más reciente.");
      }
    } else {
      entity = new MovimientoEntity();
    }

    CuentaEntity cuenta = getCuentaByRequestRecord(requestRecord);
    final StampedLock lock = entity.getLock();
    long stamp = lock.writeLock();
    try {
      BigDecimal nuevoSaldo = consultarSaldo(cuenta, requestRecord);
      if (nuevoSaldo == null) {
        throw new MovimientoException("El saldo calculado no puede ser nulo.");
      }
      MovimientoMapper.INSTANCE.updateEntityFromRequest(requestRecord, entity);
      entity.setSaldo(nuevoSaldo);
      entity.setCuenta(cuenta);
      entity.setEstado(true);
    } finally {
      lock.unlockWrite(stamp);
    }

    return entity;
  }

  private BigDecimal consultarSaldo(CuentaEntity cuenta, MovimientoRequestRecord requestRecord) {
    Optional<MovimientoEntity> lastMovimientoOpt =
        movimientoJpaRepository.findLastMovimientoByCuentaId(cuenta.getId(), requestRecord.id());

    BigDecimal saldoActual;
    if (lastMovimientoOpt.isPresent()) {
      saldoActual = lastMovimientoOpt.get().getSaldo();
    } else {
      saldoActual = cuenta.getSaldoInicial();
    }

    if (saldoActual == null) {
      throw new MovimientoException("El saldo actual no puede ser nulo.");
    }

    return calcularNuevoSaldo(saldoActual, requestRecord);
  }
}
