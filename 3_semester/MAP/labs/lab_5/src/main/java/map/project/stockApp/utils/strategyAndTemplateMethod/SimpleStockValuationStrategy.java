package map.project.stockApp.utils.strategyAndTemplateMethod;

import map.project.stockApp.model.Stock;

public class SimpleStockValuationStrategy implements StockValuationStrategy {
    private static SimpleStockValuationStrategy instance;

    private SimpleStockValuationStrategy() {
    }

    public static SimpleStockValuationStrategy getInstance() {
        if (instance == null) {
            synchronized (SimpleStockValuationStrategy.class) {
                if (instance == null) {
                    instance = new SimpleStockValuationStrategy();
                }
            }
        }
        return instance;
    }

    @Override
    public double calculatePossibleProfit(Stock stock) {
        return stock.getPrice();
    }
}
