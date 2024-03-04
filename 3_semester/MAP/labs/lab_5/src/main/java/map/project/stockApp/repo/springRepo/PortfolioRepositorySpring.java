package map.project.stockApp.repo.springRepo;

import map.project.stockApp.model.Portfolio;
import map.project.stockApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PortfolioRepositorySpring extends JpaRepository<Portfolio, Integer> {
    Portfolio findByUser(User user);
}
