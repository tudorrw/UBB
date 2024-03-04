package map.project.stockApp.repo.springRepo;

import map.project.stockApp.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepositorySpring extends JpaRepository<Stock, Integer> {
    Stock findStockByName(String name);
    Boolean existsStockByName(String name);
}
