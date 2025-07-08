package com.ubb.eventappbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "grupo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {
    @Id
    @Column(name = "id_grupo", length = 36)
    private String id;

    @Column(length = 120, unique = true)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "imagen_url", length = 250)
    private String imagenUrl;

    @ManyToOne
    @JoinColumn(name = "id_campus")
    private Campus campus;
}
