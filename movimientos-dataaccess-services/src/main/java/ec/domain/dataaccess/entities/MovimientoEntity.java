package ec.domain.dataaccess.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "movimiento", schema = "public")
@Where(clause = "(estado = true)")
public class MovimientoEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimiento_seq_gen")
  @SequenceGenerator(
      name = "movimiento_seq_gen",
      sequenceName = "movimiento_id_seq",
      allocationSize = 1)
  @Column(name = "id", nullable = false, unique = true)
  private Integer id;

  @NotNull(message = "{movimiento.fecha.notNull}")
  @Column(name = "fecha", nullable = false)
  private LocalDate fecha;

  @NotBlank(message = "{movimiento.tipo.notBlank}")
  @Column(name = "tipo", nullable = false, length = 255)
  private String tipo;

  @NotNull(message = "{movimiento.valor.notNull}")
  @Column(name = "valor", nullable = false, precision = 10, scale = 2)
  private BigDecimal valor;

  @NotNull(message = "{movimiento.saldo.notNull}")
  @Column(name = "saldo", nullable = false, precision = 10, scale = 2)
  private BigDecimal saldo;

  @Column(name = "estado")
  private Boolean estado;

  @NotNull(message = "{movimiento.cuentaId.notNull}")
  @ManyToOne
  @JoinColumn(name = "cuenta_id", nullable = false)
  private CuentaEntity cuenta;

  @Transient private StampedLock lock = new StampedLock();

  public StampedLock getLock() {
    return lock;
  }
}
