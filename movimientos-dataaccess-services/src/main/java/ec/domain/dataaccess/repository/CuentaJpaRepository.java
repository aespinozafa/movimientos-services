package ec.domain.dataaccess.repository;

import ec.domain.dataaccess.entities.CuentaEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaJpaRepository
    extends JpaRepository<CuentaEntity, Integer>, JpaSpecificationExecutor<CuentaEntity> {

  @Query("SELECT c FROM CuentaEntity c")
  List<CuentaEntity> findAllActive();

  Optional<CuentaEntity> findByNumero(String numero);
}
