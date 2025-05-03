package com.pweb.tiendaonline.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "pagos")

public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Double totalPago;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaPago;

    @Column
    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    @OneToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private Pedido pedido;

}
