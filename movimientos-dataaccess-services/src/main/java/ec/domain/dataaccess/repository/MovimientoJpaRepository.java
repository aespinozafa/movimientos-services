package ec.domain.dataaccess.repository;

import ec.domain.dataaccess.entities.MovimientoEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoJpaRepository
    extends JpaRepository<MovimientoEntity, Integer>, JpaSpecificationExecutor<MovimientoEntity> {

  @Query("SELECT m FROM MovimientoEntity m ORDER BY m.id DESC")
  List<MovimientoEntity> findAllActive();

  @Query(
      "SELECT m "
          + "FROM MovimientoEntity m "
          + "WHERE m.cuenta.clienteId = :clienteId "
          + "AND m.fecha BETWEEN :fechaInicio AND :fechaFin "
          + "ORDER BY m.id DESC")
  List<MovimientoEntity> findMovimientosByClienteAndFecha(
      Integer clienteId, LocalDate fechaInicio, LocalDate fechaFin);

  @Query(
      "SELECT m FROM MovimientoEntity m WHERE m.cuenta.id = :cuentaId AND m.estado = true ORDER BY m.fecha DESC, m.id DESC")
  List<MovimientoEntity> findLastMovimientos(Integer cuentaId);

  @Query(
      "SELECT m FROM MovimientoEntity m WHERE m.cuenta.id = :cuentaId AND m.estado = true AND m.id != :id ORDER BY m.fecha DESC, m.id DESC")
  List<MovimientoEntity> findLastMovimientosExceptId(Integer cuentaId, Integer id);

  default Optional<MovimientoEntity> findLastMovimientoByCuentaId(Integer cuentaId, Integer id) {
    List<MovimientoEntity> movimientos;

    if (id != null) {
      movimientos = findLastMovimientosExceptId(cuentaId, id);
    } else {
      movimientos = findLastMovimientos(cuentaId);
    }

    // Asegurarse de devolver solo el primer elemento o vacío
    if (movimientos != null && !movimientos.isEmpty()) {
      return Optional.of(movimientos.get(0));
    } else {
      return Optional.empty();
    }
  }
}
