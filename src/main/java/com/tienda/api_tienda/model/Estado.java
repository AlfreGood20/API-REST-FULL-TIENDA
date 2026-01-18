package com.tienda.api_tienda.model;

import com.tienda.api_tienda.utils.EstadoEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estados_venta")
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estado_id")
    private long estadoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private EstadoEnum nombre;
}
