package org.example.test2;

import org.example.model.*;
import org.example.utils.strategy.GrowthStockValuationStrategy;
import org.example.utils.strategy.StockProfit;
import org.example.utils.strategy.ValueStockValuationStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObserverTest {
    @Test
    void calculate() {
        Company company = new Company(1,"Tesla", 100, 1);
        Market market = new Market(1,"Nasdaq","New York");
        GrowthStock growthStock = new GrowthStock(1, "GrowthStock", company, market,10);
        ValueStock valueStock = new ValueStock(2, "ValueStock", company, market, 0.2);

        company.setCapitalization(1000);
        assertEquals(1000, growthStock.getPrice());

        company.setNumberShares(100);
        assertEquals(10, valueStock.getPrice());
    }
}
