package map.project.stockApp.utils.strategyAndTemplateMethod;

import map.project.stockApp.model.Stock;
import map.project.stockApp.model.ValueStock;

public class ValueStockValuationStrategy implements StockValuationStrategy {

    private static ValueStockValuationStrategy instance;

    private ValueStockValuationStrategy() {
    }

    public static ValueStockValuationStrategy getInstance() {
        if (instance == null) {
            synchronized (ValueStockValuationStrategy.class) {
                if (instance == null) {
                    instance = new ValueStockValuationStrategy();
                }
            }
        }
        return instance;
    }

    @Override
    public double calculatePossibleProfit(Stock stock) {
        double dividendRate = ((ValueStock) stock).getDividend_rate();
        return stock.getPrice() * (dividendRate + 1);
    }
}
