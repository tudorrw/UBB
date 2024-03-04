package map.project.stockApp.repo.springRepo;

import map.project.stockApp.model.HybridStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HybridStockRepositorySpring extends JpaRepository<HybridStock, Integer> {
    Boolean existsHybridStockByName(String name);
}
