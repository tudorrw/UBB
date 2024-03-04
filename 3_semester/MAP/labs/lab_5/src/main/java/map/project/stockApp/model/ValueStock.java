package map.project.stockApp.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import map.project.stockApp.utils.memento.ValueStockMemento;

@Getter
@Entity
@DiscriminatorValue("Value")
public class ValueStock extends Stock {
    private double dividend_rate;

    public ValueStock(int id, String name, Company company, Market market, double dividend_rate) {
        super(id, name, company, market);
        this.dividend_rate = dividend_rate;
    }

    public ValueStock() {

    }

    public void setDividend_rate(double dividend_rate) {
        this.dividend_rate = dividend_rate;
    }

    public ValueStockMemento createValueMemento() {
        return new ValueStockMemento(this);
    }


    public void restoreFromMemento(ValueStockMemento memento) {
        this.setId(memento.getId());
        this.setName(memento.getName());
        this.setCompany(memento.getCompany());
        this.setMarket(memento.getMarket());
        this.setDividend_rate(memento.getDividend_rate());
    }
}
