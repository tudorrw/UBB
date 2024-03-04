package org.example.utils.strategy;

import org.example.model.GrowthStock;
import org.example.model.Stock;
import org.example.utils.strategy.StockValuationStrategy;

public class GrowthStockValuationStrategy implements StockValuationStrategy {
    @Override
    public double calculatePossibleProfit(Stock stock) {
        int growthRate = ((GrowthStock) stock).getGrowth_rate();
        return stock.getPrice() + (int) (stock.getPrice() * (growthRate / 100.0));
    }
}
