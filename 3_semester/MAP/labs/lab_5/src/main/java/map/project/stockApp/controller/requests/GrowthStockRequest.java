package map.project.stockApp.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GrowthStockRequest {
    private int id;
    private String name;
    private String company;
    private String market;
    private int growth_rate;
}

