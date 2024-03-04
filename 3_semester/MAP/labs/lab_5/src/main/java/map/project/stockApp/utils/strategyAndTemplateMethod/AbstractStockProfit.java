package map.project.stockApp.utils.strategyAndTemplateMethod;

import map.project.stockApp.model.Stock;

public abstract class AbstractStockProfit {
    protected StockValuationStrategy strategy;

    public void setStrategy(StockValuationStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculateProfit(Stock stock) {
        double intermediate_profit = calculateTypeProfit(stock);
        return applyMarketConditions(intermediate_profit);
    }

    protected abstract double calculateTypeProfit(Stock stock);

    protected abstract double applyMarketConditions(double price);
}
