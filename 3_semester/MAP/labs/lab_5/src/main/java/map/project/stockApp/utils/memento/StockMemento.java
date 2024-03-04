package map.project.stockApp.utils.memento;

import lombok.Getter;
import map.project.stockApp.model.Company;
import map.project.stockApp.model.Market;
import map.project.stockApp.model.Stock;

@Getter
public class StockMemento {
    private int id;
    private String name;
    private Company company;
    private Market market;

    public StockMemento(Stock stock) {
        this.id = stock.getId();
        this.name = stock.getName();
        this.company = stock.getCompany();
        this.market = stock.getMarket();
    }

    @Override
    public String toString() {
        return "StockMemento{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company=" + company +
                ", market=" + market +
                '}';
    }
}
