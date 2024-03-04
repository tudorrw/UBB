package map.project.stockApp.repo.springRepo;

import map.project.stockApp.model.GrowthStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrowthStockRepositorySpring extends JpaRepository<GrowthStock, Integer> {
    Boolean existsGrowthStockByName(String name);
}
