package map.project.stockApp.utils.factory;

import map.project.stockApp.utils.strategyAndTemplateMethod.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StockValuationFactory {
    public static StockValuationStrategy getValuation(String type) {
        if (Objects.equals(type, "Growth")) {
            return GrowthStockValuationStrategy.getInstance();
        }
        if (Objects.equals(type, "Value")) {
            return ValueStockValuationStrategy.getInstance();
        }
        if (Objects.equals(type, "Hybrid")) {
            return HybridStockValuationStrategy.getInstance();
        }
        if (Objects.equals(type, "Simple")) {
            return SimpleStockValuationStrategy.getInstance();
        }

        return null;
    }
}
