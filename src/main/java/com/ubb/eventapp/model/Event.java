package com.ubb.eventapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "evento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "origen_tipo")
    private EventSourceType origenTipo = EventSourceType.USUARIO;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Group grupo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_creador")
    private User creador;

    @Column(length = 180, nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @Column(length = 120)
    private String lugar;

    @Column(name = "aforo_max")
    private Integer aforoMax;

    @Enumerated(EnumType.STRING)
    private Visibility visibilidad = Visibility.PUBLICO;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_validacion")
    private ValidationState estadoValidacion = ValidationState.PENDIENTE;
}
