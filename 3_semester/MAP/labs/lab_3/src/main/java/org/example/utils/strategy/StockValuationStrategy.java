package org.example.utils.strategy;

import org.example.model.Stock;

public interface StockValuationStrategy {
    double calculatePossibleProfit(Stock stock);
}
