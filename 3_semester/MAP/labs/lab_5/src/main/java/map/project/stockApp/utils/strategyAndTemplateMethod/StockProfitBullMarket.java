package map.project.stockApp.utils.strategyAndTemplateMethod;

import map.project.stockApp.model.Stock;

public class StockProfitBullMarket extends AbstractStockProfit {
    private StockValuationStrategy strategy;

    public void setStrategy(StockValuationStrategy strategy) {
        this.strategy = strategy;
    }


    @Override
    protected double calculateTypeProfit(Stock stock) {
        return strategy.calculatePossibleProfit(stock);
    }

    @Override
    protected double applyMarketConditions(double profit) {
        System.out.println(profit);
        return profit + (profit / 10 * 2);
    }
}
