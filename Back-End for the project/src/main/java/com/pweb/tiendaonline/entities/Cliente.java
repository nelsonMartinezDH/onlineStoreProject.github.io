package com.pweb.tiendaonline.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "clientes")

public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String email;

    @Column
    private String direccion;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Pedido> pedidos;

}
