package map.project.stockApp.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockRequest {
    private String stockName;
    private int stockQuantity;


}
