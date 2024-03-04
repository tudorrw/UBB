package map.project.stockApp.repo.springRepo;

import map.project.stockApp.model.ValueStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValueStockRepositorySpring extends JpaRepository<ValueStock, Integer> {
    Boolean existsValueStockByName(String name);
}
