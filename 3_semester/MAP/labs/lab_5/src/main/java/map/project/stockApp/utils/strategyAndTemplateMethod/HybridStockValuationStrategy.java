package map.project.stockApp.utils.strategyAndTemplateMethod;

import map.project.stockApp.model.HybridStock;
import map.project.stockApp.model.Stock;

public class HybridStockValuationStrategy implements StockValuationStrategy {

    private static HybridStockValuationStrategy instance;

    private HybridStockValuationStrategy() {
    }

    public static HybridStockValuationStrategy getInstance() {
        if (instance == null) {
            synchronized (HybridStockValuationStrategy.class) {
                if (instance == null) {
                    instance = new HybridStockValuationStrategy();
                }
            }
        }
        return instance;
    }

    @Override
    public double calculatePossibleProfit(Stock stock) {
        int growthRate = ((HybridStock) stock).getGrowth_rate();
        double dividendRate = ((HybridStock) stock).getDividend_rate();
        System.out.println(growthRate);
        return stock.getPrice() + (int) (stock.getPrice() * (growthRate / 50.0)) + stock.getPrice() * (dividendRate + 1);
    }
}
