package map.project.stockApp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import map.project.stockApp.utils.memento.GrowthStockMemento;

@Getter
@Entity()
@DiscriminatorValue("Growth")
public class GrowthStock extends Stock {
    private int growth_rate;

    public GrowthStock(int id, String name, Company company, Market market, int growth_rate) {
        super(id, name, company, market);
        this.growth_rate = growth_rate;
    }

    public GrowthStock() {
    }

    public GrowthStockMemento createGrowthMemento() {
        return new GrowthStockMemento(this);
    }

    public void restoreFromMemento(GrowthStockMemento memento) {
        this.setId(memento.getId());
        this.setName(memento.getName());
        this.setCompany(memento.getCompany());
        this.setMarket(memento.getMarket());
        this.setGrowth_rate(memento.getGrowth_rate());
    }

    public void setGrowth_rate(int growth_rate) {
        this.growth_rate = growth_rate;
    }
}

