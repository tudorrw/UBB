package com.vvss.FlavorFiesta.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "\"user\"",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    public enum ERole {
        USER,
        ROLE_ADMIN
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    @Enumerated(EnumType.STRING)
    private ERole role = ERole.USER;
}
