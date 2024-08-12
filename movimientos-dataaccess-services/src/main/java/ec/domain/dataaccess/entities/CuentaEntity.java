package ec.domain.dataaccess.entities;

import ec.domain.core.record.remote.ClienteTo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.concurrent.locks.StampedLock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "cuenta", schema = "public")
@Where(clause = "(estado = true)")
public class CuentaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cuenta_seq_gen")
  @SequenceGenerator(name = "cuenta_seq_gen", sequenceName = "cuenta_id_seq", allocationSize = 1)
  @Column(name = "id", nullable = false, unique = true)
  private Integer id;

  @NotBlank(message = "{cuenta.numero.notBlank}")
  @Column(name = "numero", nullable = false, length = 255)
  private String numero;

  @NotBlank(message = "{cuenta.tipo.notBlank}")
  @Column(name = "tipo", nullable = false, length = 255)
  private String tipo;

  @NotNull(message = "{cuenta.saldoInicial.notNull}")
  @Column(name = "saldo_inicial", nullable = false, precision = 10, scale = 2)
  private BigDecimal saldoInicial;

  @NotNull(message = "{cuenta.estado.notNull}")
  @Column(name = "estado", nullable = false)
  private Boolean estado;

  @NotNull(message = "{cuenta.clienteId.notNull}")
  @Column(name = "cliente_id", nullable = false)
  private Integer clienteId;

  @Transient private ClienteTo cliente;

  @Transient private StampedLock lock = new StampedLock();

  public StampedLock getLock() {
    return lock;
  }
}
