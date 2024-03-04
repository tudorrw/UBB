package map.project.stockApp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@DiscriminatorValue("1")
public class Admin extends AppUser {
    private String role;

    @Override
    public String toString() {
        return "Admin{" +
                "role='" + role + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Admin(int id, String username, String password, String role) {
        super(id, username, password);
        this.role = role;
    }
}
