package map.project.stockApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(PortfolioStockLinkId.class)
@Table(name = "portfoliostocklink")

public class PortfolioStockLink {
    @Id
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
    @Id
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
    private int quantity;

    @Override
    public String toString() {
        return "PortfolioStockLink{" +
                "stock=" + stock.getName() +
                ", quantity=" + quantity +
                '}';
    }
}
