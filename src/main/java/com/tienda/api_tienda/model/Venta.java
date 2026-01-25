package com.tienda.api_tienda.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ventas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venta_id")
    private long ventaId;

    @OneToMany(mappedBy = "ventaId", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalle;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estadoId;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuarioId;

    @ManyToOne
    @JoinColumn(name = "metodo_id", nullable = false)
    private Metodo metodoId;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente clienteId;

    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "monto_total", nullable = false, updatable = false)
    private BigDecimal montoTotal;

    @Column(name = "monto_recibido", nullable = false, updatable = false)
    private BigDecimal montoRecibido;

    @Column(name = "monto_cambio", nullable = false, updatable = false)
    private BigDecimal montoCambio;
}