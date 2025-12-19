package com.vvss.FlavorFiesta.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "recipe_id") // This column will store the foreign key referencing the Recipe table
    private Recipe recipe;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User owner;

    @NonNull
    private String content;
}
