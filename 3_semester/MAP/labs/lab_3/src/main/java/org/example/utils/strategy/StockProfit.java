package org.example.utils.strategy;

import org.example.model.Stock;
import org.example.utils.strategy.StockValuationStrategy;

public class StockProfit {
    private StockValuationStrategy strategy;

    public void setStrategy(StockValuationStrategy strategy) {
        this.strategy = strategy;
    }
    public double calculateProfit(Stock stock){
        return strategy.calculatePossibleProfit(stock);
    }
}
