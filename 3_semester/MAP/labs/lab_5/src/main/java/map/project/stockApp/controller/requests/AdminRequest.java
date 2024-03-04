package map.project.stockApp.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdminRequest {
    private int id;
    private String username;
    private String password;
    private String role;
}
