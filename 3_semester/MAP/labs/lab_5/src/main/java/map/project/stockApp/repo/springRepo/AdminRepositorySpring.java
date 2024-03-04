package map.project.stockApp.repo.springRepo;

import map.project.stockApp.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepositorySpring extends JpaRepository<Admin, Integer> {
    Admin findByUsernameAndPassword(String username, String password);

    Admin findByUsername(String username);

    Boolean existsAdminByUsername(String username);
}
