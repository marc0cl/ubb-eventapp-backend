package com.ubb.eventapp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trofeo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trophy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trofeo")
    private Integer id;

    @Column(length = 80, unique = true)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}
