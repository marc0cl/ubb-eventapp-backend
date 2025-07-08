package com.ubb.eventappbackend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "id_usuario", length = 36)
    private String id;

    @Column(name = "correo_ubb", length = 120, nullable = false, unique = true)
    private String correoUbb;

    @Column(length = 80, nullable = false)
    private String nombres;

    @Column(length = 80, nullable = false)
    private String apellidos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserState estado = UserState.ACTIVO;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "usuario_rol",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<Role> roles;
}
