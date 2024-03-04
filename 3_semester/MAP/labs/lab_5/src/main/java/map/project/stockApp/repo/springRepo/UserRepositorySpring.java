package map.project.stockApp.repo.springRepo;

import map.project.stockApp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepositorySpring extends CrudRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);

    Boolean existsUserByUsername(String name);

    User findUserByUsername(String username);

}
