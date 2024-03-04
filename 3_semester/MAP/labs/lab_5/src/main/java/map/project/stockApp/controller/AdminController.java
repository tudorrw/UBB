package map.project.stockApp.controller;

import map.project.stockApp.controller.requests.AdminRequest;
import map.project.stockApp.model.Admin;
import map.project.stockApp.model.Role;
import map.project.stockApp.service.*;
import map.project.stockApp.utils.autorization.CredentialChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController implements IPersonController {
    private AdminService adminService;
    private UserService userService;
    private ValueStockService valueStockService;
    private GrowthStockService growthStockService;
    private CompanyService companyService;
    private MarketService marketService;
    private CredentialChecker credentialChecker;

    @Autowired
    public AdminController(AdminService adminService, UserService userService, ValueStockService valueStockService, GrowthStockService growthStockService, CompanyService companyService, MarketService marketService) {
        this.adminService = adminService;
        this.userService = userService;
        this.valueStockService = valueStockService;
        this.growthStockService = growthStockService;
        this.companyService = companyService;
        this.marketService = marketService;
        this.credentialChecker = new CredentialChecker(userService, adminService);
    }

    @Override
    public void run() {

    }

    @GetMapping
    @ResponseBody
    List<Admin> seeAdmins(@RequestHeader("Authorization") String authorizationHeader) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Admin)) {
            return adminService.showAllAdmins();
        }
        return null;
    }


    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(@RequestHeader("Authorization") String authorizationHeader, @RequestBody AdminRequest adminRequest) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Super_Admin)) {
            try {
                Role role = Role.valueOf(adminRequest.getRole());
                Admin admin = new Admin(adminRequest.getId(), adminRequest.getUsername(), adminRequest.getPassword(), adminRequest.getRole());
                System.out.println(admin);
                adminService.createAdmin(admin);

            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(403).build();
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin with role " + adminRequest.getRole() + " added succesfully");
    }

    @DeleteMapping(path = "/delete/{id}")
    public String deleteAdmin(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("id") int id) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Super_Admin)) {
            adminService.deleteAdmin(id);
        }
        return "redirect:/admin";
    }

    @PutMapping(path = "/update/{username}")
    public String updateAdmin(@RequestHeader("Authorization") String authorizationHeader, @PathVariable("username") String oldUsername, @RequestBody AdminRequest adminRequest) {
        if (credentialChecker.checkCredentials(authorizationHeader, Role.Super_Admin)) {
            adminService.updateAdmin(oldUsername, adminRequest.getUsername(), adminRequest.getPassword());
        }
        return "redirect:/admins";
    }
}
