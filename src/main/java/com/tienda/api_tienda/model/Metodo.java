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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "metodo_pago")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Metodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long metodoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private MetodoEnum nombre;
}