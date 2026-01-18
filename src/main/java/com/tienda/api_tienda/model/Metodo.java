package com.tienda.api_tienda.model;

import com.tienda.api_tienda.utils.MetodoEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "metodo_pago")
public class Metodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long metodoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private MetodoEnum nombre;
}