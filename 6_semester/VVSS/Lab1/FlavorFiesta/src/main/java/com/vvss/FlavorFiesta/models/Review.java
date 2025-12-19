package com.vvss.FlavorFiesta.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User owner;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @NonNull
    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 10, message = "Rating must be at most 10")
    private int rating;

    @Column(nullable = false)
    private boolean isAnonymous;
}
