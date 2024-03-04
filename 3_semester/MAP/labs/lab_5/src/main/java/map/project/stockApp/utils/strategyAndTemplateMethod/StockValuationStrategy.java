package map.project.stockApp.utils.strategyAndTemplateMethod;

import map.project.stockApp.model.Stock;

public interface StockValuationStrategy {
    double calculatePossibleProfit(Stock stock);
}
