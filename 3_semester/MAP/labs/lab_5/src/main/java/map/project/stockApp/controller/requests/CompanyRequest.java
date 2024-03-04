package map.project.stockApp.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompanyRequest {
    private int id;
    private String name;
    private long capitalization;
    private long number_shares;
    private long number_shares_outstanding;


}
