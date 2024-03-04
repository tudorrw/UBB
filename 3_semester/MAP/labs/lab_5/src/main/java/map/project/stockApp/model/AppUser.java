package map.project.stockApp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorFormula;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "appusers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorFormula("case when role in ('Admin', 'Super_Admin') then 1 when role = 'User' then 2 else 3 end")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Column(unique = true)
    protected String username;
    protected String password;

}
