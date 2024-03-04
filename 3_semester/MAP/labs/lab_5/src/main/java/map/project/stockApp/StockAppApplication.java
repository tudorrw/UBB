package map.project.stockApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "map.project.stockApp.model")
public class StockAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockAppApplication.class, args);
	}

}
