package map.project.stockApp.utils.memento;

import lombok.Getter;
import map.project.stockApp.model.HybridStock;

@Getter
public class HybridStockMemento extends StockMemento {
    private int growth_rate;
    private double dividend_rate;

    public HybridStockMemento(HybridStock stock) {
        super(stock);
        this.growth_rate = stock.getGrowth_rate();
        this.dividend_rate = stock.getDividend_rate();
    }
}
