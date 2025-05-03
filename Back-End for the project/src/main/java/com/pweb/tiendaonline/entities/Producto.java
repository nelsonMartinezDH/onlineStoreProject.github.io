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
@Table(name = "productos")

public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String nombre;

    @Column
    private Double price;

    @Column
    private Integer stock;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<ItemPedido> itemsPedidos;

}
