package com.vvss.FlavorFiesta.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "owner_user_id") // This column will store the foreign key referencing the User table
    private User owner;

    @NonNull
    private String title;

    @NonNull
    private String ingredients;

    @NonNull
    private String instructions;
}