package org.example.utils.strategy;

import org.example.model.Stock;
import org.example.model.ValueStock;

public class ValueStockValuationStrategy implements StockValuationStrategy {
    @Override
    public double calculatePossibleProfit(Stock stock) {
        double dividendRate = ((ValueStock) stock).getDividend_rate();;
        return stock.getPrice() * (dividendRate + 1);
    }
}
