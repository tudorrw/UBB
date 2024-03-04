package map.project.stockApp.utils.memento;

import lombok.Getter;
import map.project.stockApp.model.ValueStock;

@Getter
public class ValueStockMemento extends StockMemento {
    private double dividend_rate;

    public ValueStockMemento(ValueStock stock) {
        super(stock);
        this.dividend_rate = stock.getDividend_rate();
    }

}

