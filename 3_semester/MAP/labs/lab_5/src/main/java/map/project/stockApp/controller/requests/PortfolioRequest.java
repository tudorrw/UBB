package map.project.stockApp.controller.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PortfolioRequest {
    private int id;

    private String user;
    private double cash;
    private List<String> stocks;
    private List<Integer> stockQuantities;

}
