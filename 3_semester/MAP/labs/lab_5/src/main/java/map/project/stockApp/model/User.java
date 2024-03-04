package map.project.stockApp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("2")
public class User extends AppUser {
    public User(int id, String username, String password) {
        super(id, username, password);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + username + ", password= ***** }";
    }

}