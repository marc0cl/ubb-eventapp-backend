package com.ubb.eventapp.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name = "grupo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    private Long id;

    @Column(length = 120, unique = true)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "imagen_url", length = 250)
    private String imagenUrl;

    @ManyToOne
    @JoinColumn(name = "id_campus")
    private Campus campus;

    @ManyToMany
    @JoinTable(name = "grupo_tag",
            joinColumns = @JoinColumn(name = "id_grupo"),
            inverseJoinColumns = @JoinColumn(name = "id_tag"))
    private Set<Tag> tags;
}
