package map.project.stockApp.utils.strategyAndTemplateMethod;

import map.project.stockApp.model.GrowthStock;
import map.project.stockApp.model.Stock;

public class GrowthStockValuationStrategy implements StockValuationStrategy {
    private static GrowthStockValuationStrategy instance;

    private GrowthStockValuationStrategy() {
    }

    public static GrowthStockValuationStrategy getInstance() {
        if (instance == null) {
            synchronized (GrowthStockValuationStrategy.class) {
                if (instance == null) {
                    instance = new GrowthStockValuationStrategy();
                }
            }
        }
        return instance;
    }

    @Override
    public double calculatePossibleProfit(Stock stock) {
        int growthRate = ((GrowthStock) stock).getGrowth_rate();
        System.out.println(growthRate);
        return stock.getPrice() + (int) (stock.getPrice() * (growthRate / 100.0));
    }
}
