package com.ubb.eventappbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag")
    private Integer id;

    @Column(length = 60, unique = true)
    private String nombre;

    @ManyToMany(mappedBy = "tags")
    private Set<Group> grupos;
}
