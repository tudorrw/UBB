package map.project.stockApp.repo.springRepo;


import map.project.stockApp.model.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepositorySpring extends JpaRepository<Market, Integer> {
    Market findByName(String name);

    Boolean existsMarketByName(String name);
}
