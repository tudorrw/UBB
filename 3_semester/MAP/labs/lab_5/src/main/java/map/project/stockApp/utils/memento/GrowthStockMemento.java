package map.project.stockApp.utils.memento;

import lombok.Getter;
import map.project.stockApp.model.GrowthStock;

@Getter
public class GrowthStockMemento extends StockMemento {
    private int growth_rate;

    public GrowthStockMemento(GrowthStock stock) {
        super(stock);
        this.growth_rate = stock.getGrowth_rate();
    }

}
