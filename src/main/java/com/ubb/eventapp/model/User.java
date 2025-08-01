package com.ubb.eventapp.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "correo_ubb", length = 120, nullable = false, unique = true)
    private String correoUbb;

    @Column(length = 80, nullable = false)
    private String nombres;

    @Column(length = 80, nullable = false)
    private String apellidos;

    /**
     * Optional username chosen by the user. Will be displayed on profile pages.
     */
    @Column(length = 80, unique = true)
    private String username;

    @ManyToOne
    @JoinColumn(name = "id_campus")
    private Campus campus;

    @Column(length = 255)
    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserState estado = UserState.ACTIVO;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<Role> roles;
}
