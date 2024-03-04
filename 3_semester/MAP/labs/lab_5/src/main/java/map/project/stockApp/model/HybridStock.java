package map.project.stockApp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import map.project.stockApp.utils.memento.HybridStockMemento;

@Getter
@Entity()
@DiscriminatorValue("Hybrid")
public class HybridStock extends Stock {
    private int growth_rate;
    private double dividend_rate;

    public HybridStock(int id, String name, Company company, Market market, int growth_rate, double dividend_rate) {
        super(id, name, company, market);
        this.growth_rate = growth_rate;
        this.dividend_rate = dividend_rate;
    }

    public HybridStock() {
    }

    public HybridStockMemento createHybridMemento() {
        return new HybridStockMemento(this);
    }

    public void restoreFromMemento(HybridStockMemento memento) {
        this.setId(memento.getId());
        this.setName(memento.getName());
        this.setCompany(memento.getCompany());
        this.setMarket(memento.getMarket());
        this.setGrowth_rate(memento.getGrowth_rate());
        this.setDividend_rate(memento.getDividend_rate());
    }

    public void setGrowth_rate(int growth_rate) {
        this.growth_rate = growth_rate;
    }

    public void setDividend_rate(double dividend_rate) {
        this.dividend_rate = dividend_rate;
    }
}
